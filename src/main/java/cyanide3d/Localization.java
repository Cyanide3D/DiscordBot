package cyanide3d;

import java.text.MessageFormat;
import java.util.*;

public class Localization {

    Map<Locale, ResourceBundle> bundles = new HashMap<>();

    private static final Locale DEFAULT_LOCALE = new Locale("ru", "RU");
    private Locale locale;

    //FIXME make singletone with changable locale
    public Localization(Locale locale) {
        this.locale = locale;
        loadLocale();
    }

    public String getMessage(String key, Object... params) {
        return MessageFormat.format(bundles.get(locale).getString(key), params);
    }


    public void setLocale(Locale locale) {
        this.locale = locale;
        loadLocale();
    }

    private void loadLocale() {
        if (!bundles.containsKey(locale)) {
            try {
                bundles.put(locale, ResourceBundle.getBundle("messages", locale));
            } catch (MissingResourceException ex) {
                locale = DEFAULT_LOCALE;
                //TODO log
            }
        }
    }
}
