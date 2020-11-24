package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.EnableActionService;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;


public class ListenerState extends Command {
    private static final String SEPARATOR = " : ";
    private final Localization localization = Localization.getInstance();

    public ListenerState(){
        this.name = "listenerstate";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        EnableActionService.getInstance().getActions().forEach((key, value) -> stringBuilder.append(key)
                .append(SEPARATOR)
                .append(value)
                .append(System.lineSeparator()));
        event.reply(new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter("From Defiant'S with love :)")
                .addField("Список функций:", stringBuilder.toString(), false)
                .build());
    }
}