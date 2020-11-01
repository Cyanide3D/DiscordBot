package cyanide3d;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {
    private final ResourceBundle bundle;

    //FIXME make singletone with changable locale
    public Localization(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
    }

    public String getMessage(String key, Object... params) {
        return MessageFormat.format(bundle.getString(key), params);
    }
}
