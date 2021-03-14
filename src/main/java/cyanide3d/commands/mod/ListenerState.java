package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.ActionService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class ListenerState extends Command {
    private final Localization localization = Localization.getInstance();

    public ListenerState(){
        this.name = "listenerstate";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        ActionService.getInstance().getActions(event.getGuild().getId()).forEach(action ->
                stringBuilder
                        .append(action.getAction())
                        .append(" : `")
                        .append(action.isEnabled())
                        .append("`\n"));
        event.reply( new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter("From Defiant'S with love :)")
                .addField("Список функций:", stringBuilder.toString(), false)
                .build());
    }
}