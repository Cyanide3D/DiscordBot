package cyanide3d.commands.music;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.actions.MusicBotJoin;
import cyanide3d.model.YouTube;
import cyanide3d.musicplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayCommand extends Command {

    public PlayCommand() {
        this.name = "play";
        this.aliases = new String[]{"p"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Необходимо указать название песни!")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        if (event.getGuild().getVoiceChannels().stream().filter(voiceChannel ->
                voiceChannel.getMembers().contains(event.getMember())).findAny().orElse(null) == null) {
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Для выполнения команды необходимо находится в одном канале с ботом!")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        AudioManager audioManager = event.getGuild().getAudioManager();
        if (audioManager.isConnected() && !event.getMember().getVoiceState().getChannel().equals(audioManager.getConnectedChannel())) {
            event.reply(new EmbedBuilder()
                    .setTitle(":stop_sign: Бот находится в другом канале.")
                    .setDescription(":fast_forward: Что бы позвать бота к себе используйте команду **join**")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        VoiceChannel voiceChannel = event.getGuild().getAudioManager().getConnectedChannel();
        if (voiceChannel == null)
            new MusicBotJoin(event).join();
        else if (!voiceChannel.getMembers().contains(event.getMember()))
            new MusicBotJoin(event).join();
        PlayerManager manager = PlayerManager.getInstance();
        if (manager.getGuildMusicManager(event.getGuild()).scheduler.getQueue().size() > 10) {
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Очередь переполнена.")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        try {
            manager.loadAndPlay(event.getTextChannel(), filter(event, event.getArgs()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        manager.getGuildMusicManager(event.getGuild()).player.setVolume(15);
    }

    public String filter(CommandEvent event, String input) throws IOException {
        Pattern pattern = Pattern.compile("(https?://).([\\w-]{1,32}\\.[\\w-]{1,32})[^\\s@]*\\b");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        } else {
            YouTube videoId = new ObjectMapper().readValue(new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + URLEncoder.encode(event.getArgs(), "utf-8") + "&type=video&key=AIzaSyCrxtwFXAmpY9fd4NAZEaK2lEydS1umNbU"), YouTube.class);
            return "https://www.youtube.com/watch?v=" + videoId.getItems().get(0).getId().getVideoId();
        }
    }
}
