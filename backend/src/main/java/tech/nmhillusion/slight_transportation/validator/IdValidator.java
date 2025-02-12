package tech.nmhillusion.slight_transportation.validator;

import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.n2mix.validator.StringValidator;
import tech.nmhillusion.slight_transportation.constant.IdConstant;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-02-12
 */
public abstract class IdValidator {
    public static boolean isNotSetId(Object rawId) {
        if (null == rawId) {
            return true;
        }

        final String idInString = StringUtil.trimWithNull(rawId);

        if (StringValidator.isBlank(idInString)) {
            return true;
        }

        if (IdConstant.MIN_ID > Long.parseLong(idInString)) {
            return true;
        }

        return false;
    }
}
