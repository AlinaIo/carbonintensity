package helpers;

public class PathsHelper {
    private static final String HOST = "host";
    private static final String DEFAULT_HOST = "https://api.carbonintensity.org.uk";
    private static final String ALL_REGIONS_PATH = "/regional";

    public static String getCarbonIntensityForAllRegions() {
        return getUri() + ALL_REGIONS_PATH;
    }

    private static String getHost() {
        return getEnvironmentProperty("host", DEFAULT_HOST);
    }

    public static String getUri() {
        String host = getHost();

        if (host == null) {
            return DEFAULT_HOST;
        }
        return host;
    }


    public static String getEnvironmentProperty(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value != null && !value.trim().equals("")) {
            return value;
        } else {
            value = System.getenv(key);
            return value != null && !value.trim().equals("") ? value : defaultValue;
        }
    }
}
