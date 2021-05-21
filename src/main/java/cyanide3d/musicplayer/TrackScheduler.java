package cyanide3d.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final Guild guild;
    private final BlockingQueue<AudioTrack> queue;
    private final PlayerTimerHolder timerHolder;

    public TrackScheduler(AudioPlayer player, Guild guild) {
        this.player = player;
        this.guild = guild;
        this.timerHolder = PlayerTimerHolder.getInstance();
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {
        play(track);
    }

    public void queue(List<AudioTrack> tracks) {
        if (player.getPlayingTrack() == null) {
            play(tracks.remove(0));
        }
        queue.addAll(tracks);
    }

    private void play(AudioTrack track) {
        Optional.ofNullable(player.getPlayingTrack()).ifPresentOrElse(
                ignore -> queue.offer(track),
                () -> player.startTrack(track, true)
        );

        timerHolder.stop(guild.getId());
    }

    public void nextTrack() {
        if (!player.startTrack(queue.poll(), false)) {
            timerHolder.start(guild.getAudioManager(), guild.getId());
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        player.setPaused(true);
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        player.setPaused(false);
    }

    public void clearQueue() {
        queue.clear();
    }

    public BlockingQueue<AudioTrack> getQueue() {
        return queue;
    }
}