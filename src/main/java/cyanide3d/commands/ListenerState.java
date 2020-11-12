package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.service.EnableActionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class ListenerState extends Command {
    public ListenerState(){
        this.name = "listenerstate";
    }

    @Override
    protected void execute(CommandEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        EnableActionService.getInstance().list().stream().forEach(action ->
                stringBuilder
                        .append(action.getAction())
                        .append(" : `")
                        .append(action.getState())
                        .append("`\n"));
        MessageEmbed message = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter("From Defiant'S with love :)")
                .addField("Список слушаталей:", stringBuilder.toString(), false)
                .build();
        event.reply(message);
    }
}
