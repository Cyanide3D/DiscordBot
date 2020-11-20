package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.musicplayer.GuildMusicManager;
import cyanide3d.musicplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Stop extends Command {
    public Stop() {
        this.name = "stop";
    }

    @Override
    protected void execute(CommandEvent event) {
        PlayerManager playerManager = PlayerManager.getInstance();
        if (!event.getGuild().getAudioManager().isConnected()) {
            event.getTextChannel().sendMessage("**Меня нет в голосом канале!**").queue();
            return;
        }
        if (!event.getGuild().getAudioManager().getConnectedChannel().getMembers().contains(event.getMember())) {
            event.getTextChannel().sendMessage("**Ты должен быть в одном канале со мной, дабы сделать это!**").queue();
            return;
        }
        if (playerManager.getGuildMusicManager(event.getGuild()).player.getPlayingTrack() == null){
            event.reply("Никакой трек сейчас не проигрывается.");
            return;
        }
        event.reply(new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setFooter("From Defiant'S with love :)")
                .setThumbnail("https://media.tenor.com/images/8729229b46bf9e2756692cfeff94ae64/tenor.gif")
                .addField(":no_entry:Трек остановлен:no_entry:", playerManager.getGuildMusicManager(event.getGuild()).player.getPlayingTrack().getInfo().title, false)
                .build());
        playerManager.getGuildMusicManager(event.getGuild()).player.stopTrack();
    }
}
