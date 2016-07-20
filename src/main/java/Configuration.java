import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Vlad on 20.07.2016.
 */

public class Configuration {

    public static final Logger Log = Logger.getLogger(Configuration.class);

    public static final String DRIVER_PATH = "DRIVER_PATH";
    public static final String API_KEY = "API_KEY";
    public static final String ELEMENT_TIMEOUT = "ELEMENT_TIMEOUT";
    public static final String BITCOIN_ADRESS = "BITCOIN_ADDRESS";

    private static Configuration instance = null;
    private Properties props = null;

    private Configuration() {
        props = new Properties();
        try {
            FileInputStream fis = new FileInputStream(new File("config.ini"));
            props.load(fis);
        }
        catch (Exception e) {
            System.out.println("Cannot open config files");
            Log.error("Cannot open config files ",e);
        }
    }

        public synchronized static Configuration getInstance() {
            if (instance == null) {
                instance = new Configuration();
            }
            return instance;
        }

        public synchronized String getProperty(String key) {
            String value = null;
            if (props.containsKey(key))
                value = (String) props.get(key);
            else {
                System.out.println("Property not found: "+key);
                Log.error("Property not found: "+key);
            }
            return value;
        }

}
