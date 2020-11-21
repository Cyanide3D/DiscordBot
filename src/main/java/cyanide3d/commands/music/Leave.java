package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.musicplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class Leave extends Command {
    public Leave() {
        this.name = "leave";
    }

    @Override
    protected void execute(CommandEvent event) {
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (!audioManager.isConnected()) {
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Бота нет в голосовом канале!")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        if (!audioManager.getConnectedChannel().getMembers().contains(event.getMember())) {
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Для выполнения команды необходимо находится в одном канале с ботом!")
                    .setColor(Color.ORANGE)
                    .build());
            return;
        }
        audioManager.closeAudioConnection();
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild()).player.stopTrack();
        event.reply(new EmbedBuilder()
                .setFooter("From Defiant'S with love :)")
                .setDescription(":white_check_mark: Отключился от **" + event.getGuild().getVoiceChannels().stream().filter(voice ->
                        voice.getMembers().contains(event.getMember())).findAny().orElse(null).getName() +  "**")
                .setColor(Color.ORANGE)
                .build());
    }
}
