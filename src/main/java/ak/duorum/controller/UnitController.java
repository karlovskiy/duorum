package ak.duorum.controller;

import ak.duorum.entity.Unit;
import ak.duorum.service.UnitService;
import ak.duorum.ui.message.Message;
import ak.duorum.ui.message.MessageStatus;
import ak.duorum.ui.message.MessageType;
import ak.duorum.util.UtilBean;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/15/14
 */
@Controller
public class UnitController {

    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);

    @Resource(name = "unitService")
    private UnitService unitService;

    @Resource(name = "utilBean")
    private UtilBean utilBean;

    @Resource(name = "messageSource")
    private MessageSource messageSource;

    @RequestMapping(value = "/units", method = RequestMethod.POST)
    public String createUnit(@Valid Unit unit, BindingResult result, Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "c-add-units";
        }
        String designation = unit.getDesignation();
        Unit exist = unitService.loadUnit(designation);
        if (exist != null) {
            model.addAttribute("exist", exist);
            return "c-add-units";
        }
        Long id = unitService.createUnit(unit);
        utilBean.addSuccessTextMessage(redirectAttributes, "unit.successfullyCreated", unit.getDesignation());
        return "redirect:/units/" + id;
    }

    @RequestMapping(value = "/units/{id}", method = RequestMethod.POST)
    public String updateUnit(@Valid Unit unit, BindingResult result, @PathVariable Long id, Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "c-edit-units";
        }
        unitService.updateUnit(unit);
        utilBean.addSuccessTextMessage(redirectAttributes, "unit.successfullyUpdated", unit.getDesignation());
        return "redirect:/units/" + id;
    }

    @RequestMapping(value = "/units/new", method = RequestMethod.GET)
    public String addUnit(Model model) {
        Unit unit = new Unit();
        model.addAttribute("unit", unit);
        return "c-add-units";
    }

    @RequestMapping(value = "/units/{id}/edit", method = RequestMethod.GET)
    public String editUnit(@PathVariable Long id, Model model) {
        Unit unit = unitService.loadUnit(id);
        model.addAttribute("unit", unit);
        return "c-edit-units";
    }

    @RequestMapping(value = "/units/{id}/print", method = RequestMethod.GET)
    public String printSticker(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Unit unit = unitService.loadUnit(id);
        List<String> errorFields = new ArrayList<>();
        if (StringUtils.isEmpty(unit.getLot())) {
            errorFields.add(utilBean.getMessage("unit.lot"));
        }
        if (StringUtils.isEmpty(unit.getSerialNumber())) {
            errorFields.add(utilBean.getMessage("unit.serialNumber"));
        }
        if (!errorFields.isEmpty()) {
            utilBean.addDangerTextMessage(redirectAttributes, "unit.errorPrintEmptyFields",
                    unit.getDesignation(), errorFields);
            return "redirect:/units";
        }
        if (StringUtils.length(unit.getCode()) != 4) {
            errorFields.add(utilBean.getMessage("unit.code"));
        }
        if (StringUtils.length(unit.getLot()) != 4) {
            errorFields.add(utilBean.getMessage("unit.lot"));
        }
        if (StringUtils.length(unit.getSerialNumber()) != 5) {
            errorFields.add(utilBean.getMessage("unit.serialNumber"));
        }
        if (StringUtils.length(unit.getLanguage()) != 2) {
            errorFields.add(utilBean.getMessage("unit.language"));
        }
        if (!errorFields.isEmpty()) {
            utilBean.addDangerTextMessage(redirectAttributes, "unit.errorPrintNotValidFieldsLength",
                    unit.getDesignation(), errorFields);
            return "redirect:/units";
        }
        unitService.printSticker(id);
        utilBean.addSuccessTextMessage(redirectAttributes, "unit.successfullyPrinted", unit.getDesignation());
        return "redirect:/units";
    }

    @RequestMapping(value = "/units/{id}", method = RequestMethod.GET)
    public String viewUnit(@PathVariable Long id, Model model) {
        Unit unit = unitService.loadUnit(id);
        model.addAttribute("unit", unit);
        return "c-view-units";
    }

    @RequestMapping(value = "/units", method = RequestMethod.GET)
    public String viewUnits(@RequestParam(value = "search", required = false) String search, Model model) {
        logger.info("Received request to load units.");
        model.addAttribute("units", unitService.loadUnits(search));
        model.addAttribute("search", search);
        return "c-list-units";
    }

    @RequestMapping(value = "/units/import", method = RequestMethod.GET)
    public String importUnits() {
        logger.debug("Received request to import units...");
        return "c-import-units";
    }

    @RequestMapping(value = "/units/import", method = RequestMethod.POST)
    public String importUnits(@RequestParam("file") MultipartFile file, Model model,
                              HttpServletRequest request) throws Exception {
        logger.info("Importing file " + file.getOriginalFilename());
        if (!file.isEmpty()) {
            logger.info("File size: " + file.getSize());
            ImportResultType<Unit> result = unitService.importFile(file.getInputStream());
            model.addAttribute("result", result);
            String text = messageSource.getMessage("unit.successfullyImported",
                    new Object[]{result.getAddedCount(), result.getExistsCount()}, LocaleContextHolder.getLocale());
            request.setAttribute(Message.MESSAGE_ATTRIBUTE,
                    new Message(text, MessageType.TEXT, MessageStatus.SUCCESS));
            logger.info("Importing success, added=" + result.getAddedCount() + ", exists=" + result.getExistsCount());
        }
        return "c-import-units";
    }

    @RequestMapping(value = "/units/export", method = RequestMethod.GET)
    public ModelAndView loadUnitsReport() {
        Map<String, Object> parameterMap = new HashMap<>();
        List<Unit> units = unitService.loadUnits(null);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(units, false);
        parameterMap.put("datasource", jrDataSource);
        return new ModelAndView("unitsReport", parameterMap);
    }

}
