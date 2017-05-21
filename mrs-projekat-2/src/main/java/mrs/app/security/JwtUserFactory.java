package mrs.app.security;

import java.util.ArrayList;
import java.util.List;

import mrs.app.domain.User;
import mrs.app.domain.UserType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRole()),
                true
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(UserType userType) {
        List<GrantedAuthority> retVal = new ArrayList<GrantedAuthority>();
        retVal.add(new SimpleGrantedAuthority(userType.name()));
        return retVal;
    }
}
