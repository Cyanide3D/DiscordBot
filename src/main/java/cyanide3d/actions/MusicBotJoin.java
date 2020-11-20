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

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("**Сперва зайди в голосовой канал.**").queue();
            return;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();

        if (audioManager.isConnected()) {
            audioManager.openAudioConnection(voiceChannel);
        }

        Member selfMember = event.getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            channel.sendMessageFormat("Не хватает полномочий для встепления в **%s**", voiceChannel).queue();
            return;
        }

        audioManager.openAudioConnection(voiceChannel);
    }
}
