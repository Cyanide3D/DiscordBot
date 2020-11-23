package cyanide3d.misc;

import net.dv8tion.jda.api.entities.Guild;

public class TimerToPlayer {
    private static TimerToPlayer instance;
    private Thread timer;
    private boolean active = false;
    private Guild guild;

    public boolean isActive() {
        return active;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public void setActive(boolean active) {
        this.active = active;
        if (active == true){
            if (timer.isAlive()){
                timer.interrupt();
            }
        }else {
            startTimer();
        }
    }

    private void startTimer() {
        if (timer != null){
            if (timer.isAlive()){
                timer.interrupt();
            }
        }
        timer = new Thread(() -> {
            try {
                Thread.sleep(1000*60*10);
            } catch (InterruptedException e) {
                System.out.println("Timer is interrupted...");
            }
            if (active == false) {
                guild.getAudioManager().closeAudioConnection();
            }
        });
        timer.start();
    }

    public static TimerToPlayer getInstance() {
        if (instance == null) instance = new TimerToPlayer();
        return instance;
    }
}
