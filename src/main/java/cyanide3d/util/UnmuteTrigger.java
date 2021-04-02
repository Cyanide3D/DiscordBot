package cyanide3d.util;

import net.dv8tion.jda.api.JDA;

import java.util.TimerTask;

public class UnmuteTrigger extends TimerTask {

    private final JDA jda;

    public UnmuteTrigger(JDA jda) {
        this.jda = jda;
    }

    @Override
    public void run() {
        Punishment punishment = new Punishment();
        punishment.release(jda);
    }
}
