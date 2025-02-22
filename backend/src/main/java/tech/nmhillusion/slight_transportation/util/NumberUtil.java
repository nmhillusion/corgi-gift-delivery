package tech.nmhillusion.slight_transportation.util;

import tech.nmhillusion.n2mix.validator.StringValidator;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-12
 */
public abstract class NumberUtil {

    public static String parseStringFromDoubleToLong(String doubleString) {
        if (StringValidator.isBlank(doubleString)) {
            return null;
        }

        return String.valueOf(
                (long) Double.parseDouble(doubleString)
        );
    }
}
