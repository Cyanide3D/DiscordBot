package cyanide3d.util;


import net.dv8tion.jda.api.JDA;

import java.util.List;
import java.util.Timer;

public class ObjectTimerTrigger {

    private final JDA jda;

    public ObjectTimerTrigger(JDA jda) {
        this.jda = jda;
    }

    public void execute() {
        List<SimpleTimerTask> tasks = List.of(
                new Punishment(jda)
        );

        Timer timer = new Timer();
        tasks.forEach(task -> timer.schedule(task, task.getDelay(), task.getPeriod()));
    }

}
