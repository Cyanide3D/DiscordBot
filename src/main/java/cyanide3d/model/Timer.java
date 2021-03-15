package cyanide3d.model;

import net.dv8tion.jda.api.managers.AudioManager;

public class Timer {

    private Thread timer;
    private boolean isActive;
    private final AudioManager audioManager;

    public Timer(AudioManager audioManager) {
        this.audioManager = audioManager;
        this.isActive = true;
    }

    public void startOrUpdate() {
        if (timer.isAlive()) {
            timer.interrupt();
        }
        createTimer();
        isActive = true;
    }

    public void stop() {
        if (timer.isAlive()) {
            timer.interrupt();
        }
        isActive = false;
    }

    private void createTimer() {
        timer = new Thread(() -> {
            try {
                Thread.sleep(1000*60*10);
            } catch (InterruptedException e) {
                System.out.println("Self-righteous suicide :3");
            }
            if (isActive) {
                audioManager.closeAudioConnection();
                Thread.currentThread().interrupt();
            }
        });
        timer.start();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
