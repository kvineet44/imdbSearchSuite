package ui.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author vineetkumar
 * created 6/12/2020
 * Class for Utilities methods
 */
public class Utility {
    /**
     * Method to Read the Property File and getting value
     *
     * @param fileName
     * @param key
     * @return
     */
    public String getValueOf(String fileName, String key) {
        String value = null;
        try {
            InputStream file = getClass().getClassLoader().getResourceAsStream(fileName);
            Properties properties = new Properties();
            if (file != null) {
                properties.load(file);
            }
            value = properties.getProperty(key);
        } catch (IOException e) {
            e.getStackTrace();
        }
        return value;
    }
}
