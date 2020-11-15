package cyanide3d.LogFilters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFilter extends Formatter {

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