package ac.cn.iscas.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InfoCenter {
    private static volatile InfoCenter instance;
    private static Object mutex = new Object();
    private static Map<String, List<StatisticInfo>> infoCenter = new ConcurrentHashMap<>();

    private InfoCenter() {
    }

    public static InfoCenter getInstance() {
        InfoCenter result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new InfoCenter();
            }
        }
        return result;
    }

    public static void put(String key, StatisticInfo value) {
        if (infoCenter.containsKey(key)) {
            infoCenter.get(key).add(value);
        } else {
            List<StatisticInfo> values = new ArrayList<>();
            values.add(value);
            infoCenter.put(key, values);
        }
    }

    public static List<StatisticInfo> get(String key) {
        return infoCenter.get(key);
    }

    public static Map<String, List<StatisticInfo>> get() {
        return infoCenter;
    }
}
