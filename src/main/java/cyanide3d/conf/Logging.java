package cyanide3d.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;

public class Logging {

    private static Logging instance;

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

    public static Logger getLogger(Class<?> clazz){
        if (instance == null) instance = new Logging();
        return Logger.getLogger(clazz.getName());
    }
}
