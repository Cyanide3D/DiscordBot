package conf;

import java.io.*;
import java.util.Properties;

public class Settings {

    public String getProperties(String prop){
        Properties properties = new Properties();
        String resProp = "";
        try {
        FileInputStream settFile = new FileInputStream("src/main/resources/settings.properties");
            properties.load(settFile);
            resProp = properties.getProperty(prop);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resProp;
    }
}
