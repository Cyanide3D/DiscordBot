package cyanide3d.actions;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicBotJoin {
    CommandEvent event;

    public MusicBotJoin(CommandEvent event) {
        this.event = event;
    }

    public void join(){
        TextChannel channel = event.getTextChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (audioManager.isConnected()) {
            channel.sendMessage("Я уже в голосовом канале!").queue();
            return;
        }

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Сперва зайди в голосовой канал.").queue();
            return;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = event.getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            channel.sendMessageFormat("Не хватает полномочий для встепления в **%s**", voiceChannel).queue();
            return;
        }

        audioManager.openAudioConnection(voiceChannel);
    }
}
