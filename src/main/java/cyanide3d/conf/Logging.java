package cyanide3d.conf;

import cyanide3d.Bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Logging {

    private static Logging instance;
    private Logger logger = Logger.getLogger(Bot.class.getName());

    private Logging(){
        prepare();
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepare(){
        File folder = new File("logs");
        if (!folder.exists()){
            folder.mkdir();
        }
    }

    public static Logging getInstance(){
        if (instance == null) instance = new Logging();
        return instance;
    }

    public Logger getLogger(){
        return logger;
    }
}
