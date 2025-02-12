package tech.nmhillusion.slight_transportation.util;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-12
 */
public abstract class NumberUtil {

    public static String parseStringFromDoubleToLong(String doubleString) {
        return String.valueOf(
                (long) Double.parseDouble(doubleString)
        );
    }

}
