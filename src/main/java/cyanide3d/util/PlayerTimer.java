package cyanide3d.util;

import net.dv8tion.jda.api.entities.Guild;

public class PlayerTimer {
    private static PlayerTimer instance;
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
            if (!active) {
                guild.getAudioManager().closeAudioConnection();
            }
        });
        timer.start();
    }

    public static PlayerTimer getInstance() {
        if (instance == null) instance = new PlayerTimer();
        return instance;
    }
}
