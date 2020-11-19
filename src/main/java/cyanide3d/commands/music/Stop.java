package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.musicplayer.PlayerManager;

public class Stop extends Command {
    public Stop() {
        this.name = "stop";
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
        if (PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).player.getPlayingTrack() == null){
            event.reply("Никакой трек сейчас не проигрывается.");
            return;
        }
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).player.stopTrack();
        event.reply("**Трек остановлен.**");
    }
}
