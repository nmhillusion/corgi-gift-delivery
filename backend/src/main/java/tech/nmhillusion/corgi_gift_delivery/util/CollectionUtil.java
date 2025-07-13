package tech.nmhillusion.corgi_gift_delivery.util;

import java.util.List;
import java.util.function.Function;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-12
 */
public abstract class CollectionUtil {

    public static <T> int findIndex(List<T> items, Function<T, Boolean> comparator) {
        int foundIdx = -1;

        final int itemSize = items.size();
        for (int idx = 0; idx < itemSize; ++idx) {
            if (comparator.apply(items.get(idx))) {
                foundIdx = idx;
                break;
            }
        }

        return foundIdx;
    }

}
