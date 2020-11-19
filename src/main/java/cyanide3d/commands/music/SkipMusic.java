package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.musicplayer.PlayerManager;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class SkipMusic extends Command {
    public SkipMusic() {
        this.name = "skip";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!event.getGuild().getAudioManager().isConnected()) {
            event.getTextChannel().sendMessage("Меня нет в голосом канале!").queue();
            return;
        }
        if (!event.getGuild().getAudioManager().getConnectedChannel().getMembers().contains(event.getMember())) {
            event.getTextChannel().sendMessage("Ты должен быть в одном канале со мной, дабы сделать это!").queue();
            return;
        }
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).nextTrack();
        event.reply("**Трек пропущен!**");
    }
}
