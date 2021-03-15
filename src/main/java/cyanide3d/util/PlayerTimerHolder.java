package cyanide3d.util;

import cyanide3d.model.Timer;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.HashMap;
import java.util.Map;

//TODO MAKE TIMER GREAT AGAIN
public class PlayerTimerHolder {

    private static PlayerTimerHolder instance;
    private final Map<String, Timer> timers = new HashMap<>();

    public void start(AudioManager manager, String guildId) {
        final Timer timer = timers.computeIfAbsent(guildId, e -> new Timer(manager));
        timer.startOrUpdate();
    }

    public void stop(String guildId) {
        timers.computeIfPresent(guildId, (k, v) -> {
            v.stop();
            return v;
        });
    }

    public static PlayerTimerHolder getInstance() {
        if (instance == null) instance = new PlayerTimerHolder();
        return instance;
    }
}
