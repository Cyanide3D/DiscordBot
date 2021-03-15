package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.musicplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class PauseCommand extends Command {
    public PauseCommand() {
        this.name = "pause";
    }

    @Override
    protected void execute(CommandEvent event) {
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
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).player.setPaused(true);
        event.reply("**Трек успешно поставлен на паузу!**");
    }
}
