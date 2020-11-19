package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.musicplayer.PlayerManager;

public class Pause extends Command {
    public Pause() {
        this.name = "pause";
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
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).player.setPaused(true);
        event.reply("**Трек успешно поставлен на паузу!**");
    }
}
