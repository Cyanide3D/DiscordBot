package cyanide3d.conf;

import cyanide3d.Bot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Logging {

    private static Logging instance;
    private Logger logger = Logger.getLogger(Bot.class.getName());

    private Logging(){
        prepare();
        load();
    }

    private void prepare(){
        File folder = new File("logs");
        if (!folder.exists()){
            folder.mkdir();
        }
    }

    private void load(){
        try {
            Handler handler = new FileHandler("logs/log");
            handler.setFormatter(new LogFilter());
            handler.setLevel(Level.ALL);
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.setUseParentHandlers(false);
    }

    public static Logging getInstance(){
        if (instance == null) instance = new Logging();
        return instance;
    }

    public Logger getLogger(){
        return logger;
    }

    private class LogFilter extends Formatter {

        @Override
        public String format(LogRecord record) {
            return new StringBuilder()
                    .append(new SimpleDateFormat("[dd:MM:yyyy] [HH:mm]").format(new Date()))
                    .append(" ")
                    .append(record.getLevel())
                    .append(" ")
                    .append(record.getMessage())
                    .append("\n")
                    .toString();
        }
    }

}
