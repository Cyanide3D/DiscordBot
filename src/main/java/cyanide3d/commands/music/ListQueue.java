package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import cyanide3d.musicplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.concurrent.BlockingQueue;

public class ListQueue extends Command {
    public ListQueue() {
        this.name = "queue";
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
        AudioTrack currentTrack = PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).player.getPlayingTrack();
        BlockingQueue<AudioTrack> queue = PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).scheduler.getQueue();
        if (queue.isEmpty() && currentTrack == null) {
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Очередь пуста!")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (AudioTrack audioTrack : queue) {
            if (count > 10){
                break;
            }
            stringBuilder.append(audioTrack.getInfo().title).append(" : **").append(audioTrack.getInfo().length / 1000 / 60).append(" мин.**\n");
            count++;
        }
        event.reply(new EmbedBuilder()
                .setThumbnail("https://media.tenor.com/images/8729229b46bf9e2756692cfeff94ae64/tenor.gif")
                .addField("**Текущий трек:**", currentTrack == null ? "Пусто." : currentTrack.getInfo().title, false)
                .addField("**Очередь:(Первые 11 треков)**", stringBuilder.toString(), false)
                .setColor(Color.ORANGE)
                .build());
    }
}
