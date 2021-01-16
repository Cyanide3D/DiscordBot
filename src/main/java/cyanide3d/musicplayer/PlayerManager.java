package cyanide3d.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import cyanide3d.misc.TimerToPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    private PlayerManager() {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String trackUrl) {
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                long minute = track.getInfo().length/1000/60;
                String second = String.valueOf((track.getInfo().length/1000)%60);
                String resSecond = second.length() < 2 ? "0" + second : second;
                channel.sendMessage(new EmbedBuilder()
                        .setColor(Color.ORANGE)
                        .setFooter("From Defiant'S with love :)")
                        .setThumbnail("https://media.tenor.com/images/8729229b46bf9e2756692cfeff94ae64/tenor.gif")
                        .addField(":musical_keyboard:Трек добавлен в очередь:musical_keyboard:", ":musical_note:" + track.getInfo().title + ":musical_note:", false)
                        .addField("Длительность: " + minute + ":" + resSecond + " мин.", "<" + trackUrl + ">", false)
                        .build()).queue();

                play(musicManager, track);
                TimerToPlayer.getInstance().setActive(true);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                channel.sendMessage(new EmbedBuilder()
                        .setColor(Color.ORANGE)
                        .setFooter("From Defiant'S with love :)")
                        .setThumbnail("https://media.tenor.com/images/8729229b46bf9e2756692cfeff94ae64/tenor.gif")
                        .addField(":musical_keyboard:Плейлист добавлен в очередь:musical_keyboard:", ":musical_note:" + "**Название:** " + playlist.getName() + ":musical_note:", false)
                        .addField("Треков: " + playlist.getTracks().size(), "**Первый трек:** " + playlist.getTracks().get(0).getInfo().title, false)
                        .build()).queue();

//                play(musicManager, firstTrack);
                for (AudioTrack track : playlist.getTracks()) {
                    play(musicManager,track);
                }
                TimerToPlayer.getInstance().setActive(true);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Ничего не найдено по запросу.").queue();
                TimerToPlayer.getInstance().setActive(false);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Что то пошло не так: " + exception.getMessage()).queue();
                TimerToPlayer.getInstance().setActive(false);
            }
        });


    }

    public void nextTrack() {

    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }
}