package ak.duorum.util;

import ak.duorum.spring.authentication.Principal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/11/14
 */
@Component("application")
@Scope("singleton")
public class ApplicationBean {

    private
    @Value("${application.version}")
    String version;

    public String getVersion() {
        return version;
    }

    public String getTheme() {
        return Utils.getPrincipal().getTheme();
    }

    public String getYear(){
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

}
