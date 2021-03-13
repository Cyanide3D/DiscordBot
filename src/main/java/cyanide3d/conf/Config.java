package cyanide3d.conf;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class Config {

    private final SessionFactory sessionFactory;
    Logger logger = LoggerFactory.getLogger(Config.class);
    private final Properties properties;
    private static final Config instance = new Config();
    private final File configFile;

    private Config() {
        final String profile = "hibernate.cfg.xml";
        sessionFactory = new Configuration().configure(profile).buildSessionFactory();
        properties = new Properties();
        String config = "settings.properties";
        configFile = new File(config);
        if (configFile.isFile()) {
            try {
                properties.load(new FileInputStream(configFile));
                logger.info("Loading config file...");
            } catch (IOException e) {
                logger.error("Failed load config file: ", e);
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

    public String getVkPort() {
        return properties.getProperty("VK_PORT");
    }

    public String getListenerPort() {
        return properties.getProperty("LISTENER_PORT");
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
            logger.error("Failed update prefix: ", e);
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

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
