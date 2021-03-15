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
        interruptIfAlive();
        createTimer();
        isActive = true;
    }

    public void stop() {
        interruptIfAlive();
        isActive = false;
    }

    private void interruptIfAlive() {
        if (timer != null) {
            if (timer.isAlive()) {
                timer.interrupt();
            }
        }
    }

    private void createTimer() {
        timer = new Thread(() -> {
            try {
                Thread.sleep(1000*60*1);
            } catch (InterruptedException e) {
                System.out.println("Self-righteous suuuuuuiiiiiiiciiiiiiide :3");
            }
            if (isActive) {
                audioManager.closeAudioConnection();
                Thread.currentThread().interrupt();
            }
        });
        timer.start();
    }
}
