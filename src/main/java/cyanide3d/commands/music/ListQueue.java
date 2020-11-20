package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import cyanide3d.musicplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.concurrent.BlockingQueue;

public class ListQueue extends Command {
    public ListQueue() {
        this.name = "queue";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!event.getGuild().getAudioManager().isConnected()) {
            event.getTextChannel().sendMessage("**Меня нет в голосом канале!**").queue();
            return;
        }
        if (!event.getGuild().getAudioManager().getConnectedChannel().getMembers().contains(event.getMember())) {
            event.getTextChannel().sendMessage("**Ты должен быть в одном канале со мной, дабы сделать это!**").queue();
            return;
        }
        BlockingQueue<AudioTrack> queue = PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).scheduler.getQueue();
        if (queue.isEmpty()) {
            event.reply("**Очередь пуста!**");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (AudioTrack audioTrack : queue) {
            stringBuilder.append(audioTrack.getInfo().title).append(" : **").append(audioTrack.getInfo().length / 1000 / 60).append(" мин.**\n");
        }
        event.reply(new EmbedBuilder()
                .setThumbnail(event.getGuild().getIconUrl())
                .addField("**Очередь:**", stringBuilder.toString(), false)
                .setFooter("From Defiant'S with love :)")
                .build());
    }
}
