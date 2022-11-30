package cofiguration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

public class PropertiesReader {
    private static FileReader reader;
    private static Properties properties;

    static {
        try {
            //encoding of properties file was changed to UTF-8
            reader = new FileReader("src/test/resources/testdata.properties");
            properties = new Properties();
            properties.load(reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getValueByKey(String key) {return properties.getProperty(key);}
}
