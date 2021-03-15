package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import cyanide3d.musicplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.awt.*;

public class SkipCommand extends Command {
    public SkipCommand() {
        this.name = "skip";
        this.aliases = new String[]{"s"};
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
        PlayerManager playerManager = PlayerManager.getInstance();
        AudioTrack skippedTrack = playerManager.getGuildMusicManager(event.getGuild()).player.getPlayingTrack();
        playerManager.getGuildMusicManager(event.getGuild()).nextTrack();
        AudioTrack newTrack = playerManager.getGuildMusicManager(event.getGuild()).player.getPlayingTrack();
        if (skippedTrack == null){
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Плейлист пуст!")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        event.reply(new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setDescription(":white_check_mark:Трек успешно сменён!:white_check_mark:")
                .setThumbnail("https://media.tenor.com/images/8729229b46bf9e2756692cfeff94ae64/tenor.gif")
                .addField(":o:Старый трек:o:", skippedTrack.getInfo().title, false)
                .addField(":next_track:Новый трек:next_track:", newTrack == null ? "Плейлист пуст." : newTrack.getInfo().title, false)
                .build());
    }
}
