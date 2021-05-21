package cyanide3d.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
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
        GuildMusicManager musicManager = musicManagers.computeIfAbsent(
                guild.getIdLong(),
                key -> new GuildMusicManager(playerManager, guild)
        );
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String trackUrl) {
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage(playMessage(
                        ":musical_keyboard: Трек добавлен в очередь :musical_keyboard:",
                        ":musical_note:" + track.getInfo().title + ":musical_note:",
                        "Длительность: " + audioTrackLength(track) + " мин.",
                        "<" + trackUrl + ">"
                )).queue();

                play(musicManager, track);
            }

            private String audioTrackLength(AudioTrack track) {
                String seconds = ((track.getInfo().length / 1000) % 60) + "";
                String fullSeconds = seconds.length() < 2 ? "0" + seconds : seconds;
                return track.getInfo().length / 1000 / 60 + ":" + fullSeconds;
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                if (playlist.getTracks().size() > 20) {
                    channel.sendMessage(errorMessage("Слишком много песен в плейлисте.")).queue();
                } else {
                    channel.sendMessage(playMessage(
                            ":musical_keyboard: Плейлист добавлен в очередь :musical_keyboard:",
                            ":musical_note: **Название:** " + playlist.getName() + " :musical_note:",
                            "Треков: " + playlist.getTracks().size(),
                            "**Первый трек:** " + playlist.getTracks().get(0).getInfo().title
                    )).queue();

                    play(musicManager, playlist.getTracks());
                }
            }

            private MessageEmbed playMessage(String firstTitle, String firstText, String secondTitle, String secondText) {
                return new EmbedBuilder()
                        .setColor(Color.ORANGE)
                        .setThumbnail("https://media.tenor.com/images/8729229b46bf9e2756692cfeff94ae64/tenor.gif")
                        .addField(firstTitle, firstText, false)
                        .addField(secondTitle, secondText, false)
                        .build();
            }

            @Override
            public void noMatches() {
                channel.sendMessage(errorMessage("Ничего не найдено по запросу.")).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage(errorMessage("Ошибка загрузки музыки.")).queue();
            }

            @NotNull
            private MessageEmbed errorMessage(String message) {
                return new EmbedBuilder()
                        .setDescription(":no_entry_sign: " + message)
                        .setColor(Color.ORANGE)
                        .build();
            }
        });


    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    private void play(GuildMusicManager musicManager, List<AudioTrack> tracks) {
        musicManager.scheduler.queue(tracks);
    }

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }
}