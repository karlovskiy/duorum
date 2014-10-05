package ak.duorum.spring.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/10/14
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
    private String defaultFailureUrl;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    public static final String CREDENTIALS_EXPIRED_USERNAME_KEY = "CREDENTIALS_EXPIRED_USERNAME";

    public CustomAuthenticationFailureHandler() {
    }

    public CustomAuthenticationFailureHandler(String defaultFailureUrl) {
        setDefaultFailureUrl(defaultFailureUrl);
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        if (defaultFailureUrl == null) {
            logger.debug("No failure URL set, sending 401 Unauthorized error");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " +
                    exception.getMessage());
        } else {
            logger.debug("Redirecting to " + defaultFailureUrl);
            WebUtils.setSessionAttribute(request, WebAttributes.AUTHENTICATION_EXCEPTION, exception);
            if (exception instanceof CredentialsExpiredException) {
                WebUtils.setSessionAttribute(request, CREDENTIALS_EXPIRED_USERNAME_KEY,
                        request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY));
            }
            redirectStrategy.sendRedirect(request, response, defaultFailureUrl);

        }
    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl),
                "'" + defaultFailureUrl + "' is not a valid redirect URL");
        this.defaultFailureUrl = defaultFailureUrl;
    }
}
