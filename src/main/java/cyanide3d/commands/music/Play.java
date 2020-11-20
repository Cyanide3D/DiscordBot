package cyanide3d.commands.music;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import cyanide3d.actions.MusicBotJoin;
import cyanide3d.model.YouTube;
import cyanide3d.musicplayer.PlayerManager;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Play extends Command {

    public Play() {
        this.name = "play";
        this.aliases = new String[]{"p"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            event.reply("**После команды необходимо ввести название песни!**");
            return;
        }
        if (event.getGuild().getVoiceChannels().stream().filter(voiceChannel ->
                voiceChannel.getMembers().contains(event.getMember())).findAny().orElse(null) == null){
            event.reply("**Необходимо находится в голосовом канале для использования команд бота!**");
            return;
        }
        AudioManager audioManager = event.getGuild().getAudioManager();
        if (audioManager.isConnected() && !event.getMember().getVoiceState().getChannel().equals(audioManager.getConnectedChannel())){
            event.reply("Бот находится в другом канале, что бы позвать бота к себе используйте команду **join**");
            return;
        }
        VoiceChannel voiceChannel = event.getGuild().getAudioManager().getConnectedChannel();
        if (voiceChannel == null)
            new MusicBotJoin(event).join();
        else if (!voiceChannel.getMembers().contains(event.getMember()))
            new MusicBotJoin(event).join();
        PlayerManager manager = PlayerManager.getInstance();
        if (manager.getGuildMusicManager(event.getGuild()).scheduler.getQueue().size()>6){
            event.reply("Очередь переполнена.");
            return;
        }
        try {
            YouTube videoId = new ObjectMapper().readValue(new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + URLEncoder.encode(event.getArgs(), "utf-8") + "&type=video&key=AIzaSyCrxtwFXAmpY9fd4NAZEaK2lEydS1umNbU"), YouTube.class);
            manager.loadAndPlay(event.getTextChannel(), "https://www.youtube.com/watch?v=" + videoId.getItems().get(0).getId().getVideoId());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
    }
}
