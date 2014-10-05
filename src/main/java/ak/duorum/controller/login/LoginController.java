package ak.duorum.controller.login;

import ak.duorum.entity.User;
import ak.duorum.service.UserService;
import ak.duorum.spring.authentication.Principal;
import ak.duorum.util.Utils;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static ak.duorum.spring.authentication.CustomAuthenticationFailureHandler.CREDENTIALS_EXPIRED_USERNAME_KEY;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/10/14
 */
@Controller
public class LoginController {

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute(CREDENTIALS_EXPIRED_USERNAME_KEY) != null) {
            model.addAttribute("changePassword", new ChangePassword());
        }
        return "login";
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute(CREDENTIALS_EXPIRED_USERNAME_KEY) != null) {
            model.addAttribute("changePassword", new ChangePassword());
        }
        return "login";
    }

    @RequestMapping(value = "/mustchangepassword", method = RequestMethod.POST)
    public String mustChangePassword(@Valid ChangePassword changePassword, BindingResult result,
                                     HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute(CREDENTIALS_EXPIRED_USERNAME_KEY);
        if (username == null) {
            throw new RuntimeException("Luke, we are hacked!");
        }
        if (result.hasErrors()) {
            return "login";
        }
        String newPassword = changePassword.getNewPassword();
        if (!newPassword.equals(changePassword.getRepeatNewPassword())) {
            result.rejectValue("repeatNewPassword", "login.wrongNewPassword");
            return "login";
        }
        User user = userService.loadUser(username);
        String storedPasswordHash = user.getPassword();
        String enteredPasswordHash = passwordEncoder.encodePassword(changePassword.getOldPassword(), username);
        if (!storedPasswordHash.equals(enteredPasswordHash)) {
            result.rejectValue("oldPassword", "login.wrongOldPassword");
            return "login";
        }
        user.setPassword(passwordEncoder.encodePassword(newPassword, username));
        user.setCredentialsNonExpired(true);
        userService.updateUser(user.getId(), user);
        session.removeAttribute(CREDENTIALS_EXPIRED_USERNAME_KEY);
        session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        session.setAttribute("LOGIN_INFO_MESSAGE", "login.expiredPasswordChanged");
        return "redirect:/";
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model.addAttribute("changePassword", new ChangePassword());
        return "c-changepassword-main";
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public String changePassword(@Valid ChangePassword changePassword, BindingResult result,
                                 HttpServletRequest request) {
        if (result.hasErrors()) {
            return "c-changepassword-main";
        }
        String newPassword = changePassword.getNewPassword();
        if (!newPassword.equals(changePassword.getRepeatNewPassword())) {
            result.rejectValue("repeatNewPassword", "changepassword.wrongNewPassword");
            return "c-changepassword-main";
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUser(username);
        String storedPasswordHash = user.getPassword();
        String enteredPasswordHash = passwordEncoder.encodePassword(changePassword.getOldPassword(), username);
        if (!storedPasswordHash.equals(enteredPasswordHash)) {
            result.rejectValue("oldPassword", "changepassword.wrongOldPassword");
            return "c-changepassword-main";
        }
        user.setPassword(passwordEncoder.encodePassword(newPassword, username));
        userService.updateUser(user.getId(), user);
        request.getSession().invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/changeuserinfo", method = RequestMethod.GET)
    public String changeUserInfo(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUser(username);
        ChangeUserInfo userInfo = new ChangeUserInfo();
        userInfo.setEmail(user.getEmail());
        userInfo.setFirstName(user.getFirstName());
        userInfo.setLastName(user.getLastName());
        userInfo.setTheme(user.getTheme());
        userInfo.setLanguage(user.getLanguage());
        model.addAttribute("changeUserInfo", userInfo);
        return "c-changeuserinfo-main";
    }

    @RequestMapping(value = "/changeuserinfo", method = RequestMethod.POST)
    public String changeUserInfo(@Valid ChangeUserInfo changeUserInfo, BindingResult result,
                                 HttpServletRequest request) {
        if (result.hasErrors()) {
            return "c-changeuserinfo-main";
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUser(username);
        user.setEmail(changeUserInfo.getEmail());
        user.setFirstName(changeUserInfo.getFirstName());
        user.setLastName(changeUserInfo.getLastName());
        user.setTheme(changeUserInfo.getTheme());
        user.setLanguage(changeUserInfo.getLanguage());
        userService.updateUser(user.getId(), user);
        Principal principal = Utils.getPrincipal();
        principal.setTheme(user.getTheme());
        principal.setLanguage(user.getLanguage());
        WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
                StringUtils.parseLocaleString(principal.getLanguage()));
        return "redirect:/";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "c-contact-main";
    }

}
