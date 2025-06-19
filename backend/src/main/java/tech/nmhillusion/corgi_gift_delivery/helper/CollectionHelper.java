package tech.nmhillusion.corgi_gift_delivery.helper;

import tech.nmhillusion.n2mix.util.StringUtil;

import java.util.Map;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-02-22
 */
public abstract class CollectionHelper {

    public static String getStringOrNullIfAbsent(Map<?, ?> mCollection, Object key) {
        if (null == key || !mCollection.containsKey(key)) {
            return null;
        }

        return StringUtil.trimWithNull(mCollection.get(key));
    }

}
