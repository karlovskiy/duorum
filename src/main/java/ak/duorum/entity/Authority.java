package ak.duorum.entity;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/10/14
 */
public enum Authority implements GrantedAuthority {

    ADMINISTRATOR(0x1, "ADMINISTRATOR"),
    UNITS(0x2, "UNITS"),
    PRINT_STICKERS(0x4, "PRINT_STICKERS");

    private int code;
    private String name;

    private Authority(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public static Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Authority> authorities = new AuthorityList();
        Collections.addAll(authorities, values());
        return authorities;
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(int mask) {
        Collection<Authority> authorities = new AuthorityList();
        for (Authority authority : values()) {
            if ((mask & authority.getCode()) != 0) {
                authorities.add(authority);
            }
        }
        return authorities;
    }

    public static int getAuthorityMask(String authorities) {
        int authorityMask = 0;
        Collection<Authority> validatedAuthorities = makeValidatedAuthorities(authorities);
        for (Authority authority : validatedAuthorities) {
            authorityMask |= authority.getCode();
        }
        return authorityMask;
    }

    private static Collection<Authority> makeValidatedAuthorities(String authorities) {
        Collection<Authority> result = new HashSet<>();
        if (StringUtils.isNotEmpty(authorities)) {
            for (Authority authority : values()) {
                for (String s : authorities.split(",")) {
                    if (authority.getAuthority().equalsIgnoreCase(StringUtils.trimToEmpty(s))) {
                        result.add(authority);
                    }
                }
            }
        }
        return result;
    }

    private static class AuthorityList extends ArrayList<Authority> {

        private static final long serialVersionUID = -3058194054128151316L;

        @Override
        public String toString() {
            Iterator<Authority> it = iterator();
            if (!it.hasNext()) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (; ; ) {
                Authority e = it.next();
                sb.append(e.getAuthority());
                if (!it.hasNext()) {
                    return sb.toString();
                }
                sb.append(',').append(' ');
            }
        }
    }
}
