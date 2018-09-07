package restclients;

import helpers.PathsHelper;
import helpers.RestUtil;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


//Corresponding to following endpoint: https://api.carbonintensity.org.uk/regional
public class RegionalResource {
    static String newLine = System.getProperty("line.separator");
    static Map<Object, Object> map = new HashMap<>();
    static Map<Object, Object> forecast = new HashMap<>();

    //Returns a list with intensity values for all regions
    public static ArrayList getCarbonIntensityForAllRegions() {
        Response res = RestAssured.get(PathsHelper.getCarbonIntensityForAllRegions());
        JsonPath jp = RestUtil.getJsonPath(res);
        ArrayList regions = jp.get("data.regions");
        ArrayList regionsList = (ArrayList) regions.get(0);
        return regionsList;
    }

    //Returns a map with "shortname" and "intensity" for all regions
    public static Map<Object, Object> getShortnameAndIntensity(ArrayList regionsList) {
        regionsList.forEach(region -> {
            map.put(((HashMap) region).get("shortname"), ((HashMap) region).get("intensity"));
        });
        return map;
    }

    //Returns a map with "shortname" and "forecast" values for all regions
    public static Map<Object, Object> getIntensityValueForecast(ArrayList regionsList) {
        regionsList.forEach((Object region) -> {
            forecast = (Map<Object, Object>) ((HashMap) region).get("intensity");
            map.put(((HashMap) region).get("shortname"), forecast.get("forecast"));
        });
        return map;
    }

    //Returns a map sorted on "forecast" values
    public static Map<Object, Object> sortByValue(Map<Object, Object> map) {
        Map<Object, Object> sortedMap = RestUtil.sortByValueDescending(map);
        return sortedMap;
    }

    //Returns a map with "shortname" and "generationmix" for all regions
    public static Map<Object, Object> getShortnameAndGenerationMix(ArrayList regionsList) {
        regionsList.forEach(region -> {
            map.put(((HashMap) region).get("shortname"), ((HashMap) region).get("generationmix"));
        });
        return map;
    }

    //Prints regions where generation mix is different than 100% 0r that generation mix is 100 in all regions
    public static void AssertGenerationMixDifferent100(Map<Object, Object> map) {
        final float[] sum = {0};
        final int[] counter = {0};

        map.forEach((key, value) -> {
            Map<Object, Object> generationmix = new HashMap<>();
            sum[0] = 0;
            ArrayList otherElements = (ArrayList) value;
            otherElements.forEach(elem -> {
                generationmix.put(((HashMap) elem).get("fuel"), ((HashMap) elem).get("perc"));
            });
            sum[0] = RestUtil.calculateFloatSumOfValues(generationmix);
            if (sum[0] == 100.0f) {
                counter[0]++;
            } else {
                System.out.println("Generation mix sum is different than 100% in " + key + " : " + sum[0]);
            }
        });

        if (counter[0] == 0) {
            System.out.println("Generation mix is 100 in all regions.");
        }
    }

    //Sorts on fuel type
    public static void sortOnFuelType(Map<Object, Object> map) {
        final float[] sum = {0};
        Map<Object, Object> fuelMap = new HashMap<>();
        Map<Object, Object> oneFuelMap = new HashMap<>();
        final Map<Object, Object>[] sortedFuelMap = new Map[]{new HashMap<>()};

        map.forEach((key, value) -> {
            Map<Object, Object> generationmix = new HashMap<>();

            sum[0] = 0;
            final ArrayList[] otherElements = {(ArrayList) value};
            otherElements[0].forEach(elem -> {
                generationmix.put(((HashMap) elem).get("fuel"), ((HashMap) elem).get("perc"));
            });
            generationmix.keySet().forEach(fuel -> {
                oneFuelMap.put(key, generationmix.get(fuel));
            });
        });

        sortedFuelMap[0] = RestUtil.sortByFloatValueDescending(oneFuelMap);
        System.out.println("sortedFuelMap: " + sortedFuelMap[0]);
    }

}
