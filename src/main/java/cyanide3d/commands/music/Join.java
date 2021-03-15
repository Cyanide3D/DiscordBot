package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.actions.MusicBotJoin;
import cyanide3d.util.PlayerTimerHolder;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Join extends Command {
    public Join() {
        this.name = "join";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (new MusicBotJoin(event).join()) {
            event.reply(new EmbedBuilder()
                    .setDescription(":white_check_mark: Подключился к **" + event.getGuild().getVoiceChannels().stream().filter(voiceChannel ->
                            voiceChannel.getMembers().contains(event.getMember())).findAny().orElse(null).getName() +  "**")
                    .setColor(Color.ORANGE)
                    .build());
            PlayerTimerHolder timerHolder = PlayerTimerHolder.getInstance();
            timerHolder.start(event.getGuild().getAudioManager(), event.getGuild().getId());
        }
    }
}
