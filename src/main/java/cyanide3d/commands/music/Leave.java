package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.musicplayer.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class Leave extends Command {
    public Leave() {
        this.name = "leave";
    }

    @Override
    protected void execute(CommandEvent event) {
        TextChannel channel = event.getTextChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (!audioManager.isConnected()) {
            channel.sendMessage("Меня нет в голосом канале!").queue();
            return;
        }
        VoiceChannel voiceChannel = audioManager.getConnectedChannel();
        if (!voiceChannel.getMembers().contains(event.getMember())) {
            channel.sendMessage("Ты должен быть в одном канале со мной, дабы сделать это!").queue();
            return;
        }
        audioManager.closeAudioConnection();
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).player.stopTrack();
        channel.sendMessage("Я ушёл.").queue();
    }
}
