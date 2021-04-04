package cyanide3d.util;


import net.dv8tion.jda.api.JDA;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ObjectTimerTrigger {

    private final JDA jda;

    public ObjectTimerTrigger(JDA jda) {
        this.jda = jda;
    }

    public void execute() {

        List<TimerTask> to = List.of(
                new Punishment(jda)
        );
        Timer timer = new Timer();

        to.forEach(object ->  {
            TriggeredObject triggeredObj = (TriggeredObject) object;
            timer.schedule(object, triggeredObj.getDelay(), triggeredObj.getPeriod());
        });
    }

}
