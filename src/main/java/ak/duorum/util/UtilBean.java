package ak.duorum.util;

import ak.duorum.ui.message.Message;
import ak.duorum.ui.message.MessageStatus;
import ak.duorum.ui.message.MessageType;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/27/14
 */
@Component("utilBean")
@Scope("singleton")
public class UtilBean {

    @Resource(name = "messageSource")
    private MessageSource messageSource;

    public String getMessage(String code, Object... params) {
        return messageSource.getMessage(code, params, LocaleContextHolder.getLocale());
    }

    public void addDangerTextMessage(RedirectAttributes redirectAttributes, String code, Object... params) {
        addMessage(redirectAttributes, MessageType.TEXT, MessageStatus.DANGER, code, params);
    }

    public void addSuccessTextMessage(RedirectAttributes redirectAttributes, String code, Object... params) {
        addMessage(redirectAttributes, MessageType.TEXT, MessageStatus.SUCCESS, code, params);
    }

    public void addMessage(RedirectAttributes redirectAttributes, MessageType messageType, MessageStatus messageStatus,
                           String code, Object... params) {
        String text = getMessage(code, params);
        redirectAttributes.addFlashAttribute(Message.MESSAGE_ATTRIBUTE,
                new Message(text, messageType, messageStatus));
    }

}
