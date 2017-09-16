package com.support.framework.support;

import java.util.HashMap;
import java.util.Map;

public final class ThreadLocalMap {

    private static ThreadLocal<HashMap<String, Object>> threadLocalMap = ThreadLocal.withInitial(HashMap::new);

    private ThreadLocalMap() {}

    public static void cleanup() {
        threadLocalMap.remove();
    }

    public static <T> T getItem(String key, Class<T> type) {
        return type.cast(getMap().get(key));
    }

    public static Map<String, Object> getMap() {
        return threadLocalMap.get();
    }
}
