package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
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
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Бота нет в голосовом канале!")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        if (!event.getGuild().getAudioManager().getConnectedChannel().getMembers().contains(event.getMember())) {
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Для выполнения команды необходимо находится в одном канале с ботом!")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        if (playerManager.getGuildMusicManager(event.getGuild()).player.getPlayingTrack() == null){
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Никакой трек сейчас не проигрывается.")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        event.reply(new EmbedBuilder()
                .setColor(Color.ORANGE)
                .addField(":no_entry:Трек остановлен:no_entry:", playerManager.getGuildMusicManager(event.getGuild()).player.getPlayingTrack().getInfo().title, false)
                .build());
        playerManager.getGuildMusicManager(event.getGuild()).player.stopTrack();
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).scheduler.clearQueue();
    }
}
