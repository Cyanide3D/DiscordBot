package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.musicplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class ClearQueueCommand extends Command {
    public ClearQueueCommand() {
        this.name = "clearqueue";
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
        if (PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).scheduler.getQueue().isEmpty()) {
            event.reply(new EmbedBuilder()
                    .setDescription(":white_check_mark: Очередь чиста!")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).scheduler.clearQueue();
        event.reply(new EmbedBuilder()
                .setDescription(":white_check_mark: Очередь очищена!")
                .setColor(Color.ORANGE)
                .build());
    }
}
