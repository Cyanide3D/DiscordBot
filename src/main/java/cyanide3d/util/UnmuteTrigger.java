package cyanide3d.util;

import net.dv8tion.jda.api.JDA;

import java.util.TimerTask;

public class UnmuteTrigger extends TimerTask {

    private final JDA jda;
    private final Punishment punishment;

    public UnmuteTrigger(JDA jda, Punishment punishment) {
        this.jda = jda;
        this.punishment = punishment;
    }

    @Override
    public void run() {
        punishment.release(jda);
    }
}
