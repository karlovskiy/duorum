package ak.duorum.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/8/14
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    private static final long serialVersionUID = -9168313251581926248L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE", insertable = false, updatable = false)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFICATION_DATE", insertable = false, updatable = false)
    private Date lastModificationDate;

    @NotEmpty
    @Column(name = "USERNAME", nullable = false, unique = true, length = 128)
    private String username;

    @Column(name = "PASSWORD", length = 512)
    private String password;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATUS", nullable = false)
    private UserStatus userStatus;

    @Column(name = "AUTHORITY", nullable = false)
    private int authorityMask;

    @Column(name = "FIRST_NAME", length = 64)
    private String firstName;

    @Column(name = "LAST_NAME", length = 32)
    private String lastName;

    @Email
    @Column(name = "EMAIL", length = 128)
    private String email;

    @Column(name = "CREDENTIALS_NON_EXPIRED", nullable = false)
    private boolean credentialsNonExpired;

    @NotEmpty
    @Column(name = "THEME", length = 64)
    private String theme;

    @NotEmpty
    @Column(name = "LANGUAGE", length = 8)
    private String language;

    @Transient
    public Collection<? extends GrantedAuthority> getAllAuthorities() {
        return Authority.getAuthorities();
    }

    @Transient
    public Collection<? extends GrantedAuthority> getAuthoritiesCollection() {
        return Authority.getAuthorities(authorityMask);
    }

    @Transient
    public String getAuthorities() {
        return Authority.getAuthorities(authorityMask).toString();
    }

    @Transient
    public void setAuthorities(String authorities) {
        authorityMask = Authority.getAuthorityMask(authorities);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public int getAuthorityMask() {
        return authorityMask;
    }

    public void setAuthorityMask(int authorityMask) {
        this.authorityMask = authorityMask;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", lastModificationDate=" + lastModificationDate +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userStatus=" + userStatus +
                ", authorityMask=" + authorityMask +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", theme='" + theme + '\'' +
                '}';
    }
}
