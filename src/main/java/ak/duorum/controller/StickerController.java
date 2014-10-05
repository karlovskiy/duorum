package ak.duorum.controller;

import ak.duorum.entity.Unit;
import ak.duorum.service.StickerService;
import ak.duorum.ui.message.Message;
import ak.duorum.ui.message.MessageStatus;
import ak.duorum.ui.message.MessageType;
import ak.duorum.util.UtilBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/26/14
 */
@Controller
public class StickerController {

    private static final Logger logger = LoggerFactory.getLogger(StickerController.class);

    @Resource(name = "stickerService")
    private StickerService stickerService;

    @Resource(name = "utilBean")
    private UtilBean utilBean;

    @RequestMapping(value = "/stickers", method = RequestMethod.GET)
    public String viewUnits(Model model) {
        logger.info("Start handling request to load stickers.");
        model.addAttribute("stickers", stickerService.loadStickers());
        logger.info("Request to load stickers successfully handled.");
        return "c-list-stickers";
    }

    @RequestMapping(value = "/stickers/{id}/remove", method = RequestMethod.GET)
    public String removeSticker(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        logger.info("Start handling request to remove sticker [unit id={}].", id);
        Unit unit = stickerService.removeSticker(id);
        utilBean.addSuccessTextMessage(redirectAttributes, "sticker.successfullyRemoved", unit.getDesignation());
        logger.info("Request to remove sticker successfully handled [unit id={}].", id);
        return "redirect:/stickers";
    }

    @RequestMapping(value = "/stickers/removeall", method = RequestMethod.GET)
    public String removeAllStickers(RedirectAttributes redirectAttributes) {
        logger.info("Start handling request to remove sticker for all units.");
        long removed = stickerService.removeAllStickers();
        if (removed != 0) {
            utilBean.addSuccessTextMessage(redirectAttributes, "sticker.allSuccessfullyRemoved", removed);
        }
        logger.info("Request to remove stickers for all units successfully handled.");
        return "redirect:/stickers";
    }
}
