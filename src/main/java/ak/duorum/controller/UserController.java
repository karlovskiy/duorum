package ak.duorum.controller;

import ak.duorum.entity.User;
import ak.duorum.service.UserService;
import ak.duorum.util.UtilBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/14/14
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "utilBean")
    private UtilBean utilBean;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String viewUsers(Model model) {
        logger.info("Received request to load users.");
        model.addAttribute("users", userService.loadUsers());
        return "c-list-users";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.loadUser(id);
        model.addAttribute("user", user);
        return "c-view-users";
    }

    @RequestMapping(value = "/users/{id}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.loadUser(id);
        model.addAttribute("user", user);
        return "cm-edit-users-users";
    }

    @RequestMapping(value = "/users/new", method = RequestMethod.GET)
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "cm-add-users-users";
    }

    @RequestMapping(value = "/users/{id}/block", method = RequestMethod.GET)
    public String blockUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userService.blockUser(id);
        utilBean.addSuccessTextMessage(redirectAttributes, "user.successfullyBlocked", user.getUsername());
        return "redirect:/users/" + id;
    }

    @RequestMapping(value = "/users/{id}/unblock", method = RequestMethod.GET)
    public String unblockUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userService.unblockUser(id);
        utilBean.addSuccessTextMessage(redirectAttributes, "user.successfullyUnblocked", user.getUsername());
        return "redirect:/users/" + id;
    }

    @RequestMapping(value = "/users/{id}/reset", method = RequestMethod.GET)
    public String resetUserPassword(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        User user = userService.resetUserPassword(id);
        utilBean.addSuccessTextMessage(redirectAttributes, "user.successfullyPasswordReset", user.getUsername());
        return "redirect:/users/" + id;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String createUser(@Valid User user, BindingResult result, Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cm-add-users-users";
        }
        String username = user.getUsername();
        User existsUser = userService.loadUser(username);
        if (existsUser != null) {
            model.addAttribute("existsUser", existsUser);
            return "cm-add-users-users";
        }
        Long id = userService.createUser(user);
        utilBean.addSuccessTextMessage(redirectAttributes, "user.successfullyCreated", user.getUsername());
        return "redirect:/users/" + id;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result, @PathVariable Long id, Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cm-edit-users-users";
        }
        User oldUser = userService.loadUser(id);
        oldUser.setAuthorities(user.getAuthorities());
        oldUser.setEmail(user.getEmail());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setLanguage(user.getLanguage());
        oldUser.setTheme(user.getTheme());
        userService.updateUser(id, oldUser);
        utilBean.addSuccessTextMessage(redirectAttributes, "user.successfullyUpdated", user.getUsername());
        return "redirect:/users/" + id;
    }
}
