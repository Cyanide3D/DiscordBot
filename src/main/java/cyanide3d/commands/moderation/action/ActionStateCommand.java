package cyanide3d.commands.moderation.action;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.ActionService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

public class ActionStateCommand extends Command {
    private final Localization localization = Localization.getInstance();

    public ActionStateCommand(){
        this.name = "state";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        ActionService.getInstance().getActions(event.getGuild().getId()).forEach(action ->
                stringBuilder
                        .append(StringUtils.substringBefore(action.getAction(), "_event").toUpperCase())
                        .append(" : `")
                        .append(action.isEnabled())
                        .append("`\n"));
        event.reply( new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setFooter("Для активации: $activate")
                .setThumbnail(event.getGuild().getIconUrl())
                .addField("Список функций:", stringBuilder.toString(), false)
                .build());
    }
}