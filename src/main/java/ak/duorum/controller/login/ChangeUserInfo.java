package ak.duorum.controller.login;

import org.hibernate.validator.constraints.Email;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/14/14
 */
public class ChangeUserInfo {
    @Email
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String theme;
    private String language;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
