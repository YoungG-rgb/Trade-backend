package kg.tech.tradebackend.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {
    private final static String ANONYMOUS_USER = "anonymousUser";

    public static String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean isAnonymous(){
        return ANONYMOUS_USER.equals(getAuthenticatedUsername());
    }
}

