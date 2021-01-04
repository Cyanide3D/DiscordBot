package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.actions.MusicBotJoin;
import cyanide3d.misc.TimerToPlayer;
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
            TimerToPlayer.getInstance().setActive(false);
        }
    }
}