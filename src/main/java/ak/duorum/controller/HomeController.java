package ak.duorum.controller;

import ak.duorum.entity.Unit;
import ak.duorum.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/13/14
 */
@Controller
public class HomeController {

    private static final int LAST_UNITS_COUNT = 5;

    @Resource(name = "unitService")
    private UnitService unitService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        List<Unit> lastCreated =   unitService.loadLastCreated(LAST_UNITS_COUNT);
        List<Unit> lastModified = unitService.loadLastModified(LAST_UNITS_COUNT);
        model.addAttribute("lastCreated", lastCreated);
        model.addAttribute("lastModified", lastModified);
        return "c-home-main";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied() {
        return "403";
    }
}
