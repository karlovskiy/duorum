package ak.duorum.util;

import ak.duorum.spring.authentication.Principal;
import ak.duorum.ui.message.Message;
import ak.duorum.ui.message.MessageStatus;
import ak.duorum.ui.message.MessageType;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/20/14
 */
public class Utils {


    public static Principal getPrincipal() {
        return (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }



    private Utils() {
    }
}
