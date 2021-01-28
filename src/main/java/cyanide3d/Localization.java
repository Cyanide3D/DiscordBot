package cyanide3d;

import cyanide3d.conf.Logging;

import java.text.MessageFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Localization {

    Logger logger = Logging.getLogger(this.getClass());
    private static final Locale DEFAULT_LOCALE = new Locale("ru", "RU");
    private static final Localization instance = new Localization(DEFAULT_LOCALE);
    private final Map<Locale, ResourceBundle> bundles = new HashMap<>();
    private Locale locale;

    public static Localization getInstance() {
        return instance;
    }

    public static Localization getInstance(Locale locale) {
        if (!instance.locale.equals(locale)) {
            instance.setLocale(locale);
        }
        return instance;
    }

    private Localization(Locale locale) {
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
                logger.info("Load locale: " + locale.toString());
            } catch (MissingResourceException ex) {
                locale = DEFAULT_LOCALE;
                logger.log(Level.WARNING, "Missing Resources With locale: ", ex);
            }
        }
    }
}
