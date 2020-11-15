package cyanide3d.conf;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

    Logger logger = Logging.getInstance().getLogger();
    private final Properties properties;
    private static final Config instance = new Config();
    private final File configFile;

    private Config() {
        properties = new Properties();
        String config = "settings.properties";
        configFile = new File(config);
        if (configFile.isFile()) {
            try {
                properties.load(new FileInputStream(configFile));
            } catch (IOException e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "Failed load config file: ", e);
            }
        } else {
            try {
                properties.store(new FileWriter(configFile), "default autogenerated config");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Config getInstance() {
        return instance;
    }

    public String getToken() {
        return properties.getProperty("TOKEN");
    }

    public String getOwner() {
        return properties.getProperty("OWNER_ID");
    }

    public String getPrefix() {
        return properties.getProperty("PREFIX");
    }

    public void setPrefix(String prefix) {
        properties.setProperty("PREFIX", prefix);
        try {
            properties.store(new FileWriter(configFile), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return properties.getProperty("database.url");
    }

    public String getUsename() {
        return properties.getProperty("database.username");
    }

    public String getPassword() {
        return properties.getProperty("database.password");
    }
}
