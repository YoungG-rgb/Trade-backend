package kg.tech.tradebackend.utils;

import kg.tech.tradebackend.domain.exceptions.ValidationException;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class BaseValidator {
    public static final String EMAIL_REGEX = "^[a-zA-Z\\d._%+-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";

    public static boolean isEmpty(final CharSequence... charSequences) {
        return Arrays.stream(charSequences).allMatch(CharSequence::isEmpty);
    }

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
}
