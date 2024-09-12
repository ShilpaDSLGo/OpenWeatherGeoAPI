package api.utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	private static Properties properties;
    private final static String propertyFilePath = System.getProperty("user.dir") + "/src/test/resources/properties/Config.properties";

    // Initialize the properties
    public static Properties init_prop() throws IOException {
        if (properties == null) {
            properties = new Properties();
            FileInputStream ip = new FileInputStream(propertyFilePath);
            properties.load(ip);
        }
        return properties;
    }

    // Get the Base URL for OpenWeather API
    public static String getBaseURL() throws IOException {
        return init_prop().getProperty("baseURL");
    }

    // Get the API key from the config file
    public static String getAPIKey() throws IOException {
        return init_prop().getProperty("apiKey");
    }

	}
