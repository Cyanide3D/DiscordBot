package cyanide3d.actions;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class MusicBotJoin {
    CommandEvent event;

    public MusicBotJoin(CommandEvent event) {
        this.event = event;
    }

    public boolean join(){
        TextChannel channel = event.getTextChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Сперва зайди в голосовой канал.")
                    .setColor(Color.ORANGE)
                    .build());
            return false;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();

        if (voiceChannel.equals(audioManager.getConnectedChannel())){
            event.reply(new EmbedBuilder()
                    .setDescription(":stop_sign: Бот с тобой в одном канале!")
                    .setColor(Color.ORANGE)
                    .build());
            return false;
        }

        Member selfMember = event.getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            channel.sendMessageFormat("Не хватает полномочий для встепления в **%s**", voiceChannel).queue();
            return false;
        }

        audioManager.openAudioConnection(voiceChannel);
        return true;
    }
}
