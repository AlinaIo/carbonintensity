package helpers;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestUtil {
    //Returns JsonPath, useful for finding values in a json
    public static JsonPath getJsonPath (Response res) {
        String json = res.asString();
        //System.out.print("returned json: " + json +"\n");
        return new JsonPath(json);
    }

    //Returns a map sorted by values and descending
    public static Map<Object, Object> sortByValueDescending(Map<Object, Object> unsortMap) {
        Map<Object, Object> sortedMap = new LinkedHashMap<Object, Object>();

        Comparator<Map.Entry<Object, Object>> byValue = (entry1, entry2) -> (((Integer) entry1.getValue()).compareTo((Integer) entry2.getValue()));

        List<Map.Entry<Object, Object>> val = unsortMap
                .entrySet()
                .stream()
                .sorted(byValue.reversed())
                .collect(Collectors.toList());

        val.forEach(res -> sortedMap.put(res.getKey(), res.getValue()));
        return sortedMap;
    }

    //Returns a float map sorted by values and descending
    public static Map<Object, Object> sortByFloatValueDescending(Map<Object, Object> unsortMap) {
        Map<Object, Object> sortedMap = new LinkedHashMap<Object, Object>();

        Comparator<Map.Entry<Object, Object>> byValue = (entry1, entry2) -> {
            float value1 = ((Number) entry1.getValue()).floatValue();
            float value2 = ((Number) entry2.getValue()).floatValue();
            if (value1 < value2) return -1;
            if (value1 > value2) return 1;
            return 0;
        };

        List<Map.Entry<Object, Object>> val = unsortMap
                .entrySet()
                .stream()
                .sorted(byValue.reversed())
                .collect(Collectors.toList());

        val.forEach(res -> sortedMap.put(res.getKey(), res.getValue()));
        return sortedMap;
    }

    public static float calculateFloatSumOfValues(Map<Object, Object> map) {
        final float[] sum = {0};
        map.values().forEach(val -> {
            String conv = val.toString();
            float floatValue = Float.parseFloat(conv);
            sum[0] = Float.sum(sum[0], floatValue);
        });

        return sum[0];
    }

    public void test () {
    }
}
