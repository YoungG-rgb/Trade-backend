package kg.tech.tradebackend.utils;

import kg.tech.tradebackend.domain.exceptions.ValidationException;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class BaseValidator {

    public static final String NUMBER_CHECK_REGEXP = "^996(70|50)+[\\d]{7}";
    public static final String EMAIL_PATTERN = "^[a-zA-Z\\d._%+-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";

    public static boolean isValidNumber(String number) throws ValidationException {
        if (number.matches(NUMBER_CHECK_REGEXP)) return true;
        throw new ValidationException("Неверный номер телефона");
    }

    public static boolean isEmpty(final CharSequence... charSequences) {
        return Arrays.stream(charSequences).allMatch(CharSequence::isEmpty);
    }

    public static void isValidEmail(String email) throws Exception {
        if (!email.matches(EMAIL_PATTERN)) throw new Exception("Неверный формат mail адреса");
    }
}
