package cyanide3d.commands.moderation.action;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.model.ActionEntity;
import cyanide3d.repository.service.ActionService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

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
        event.reply(getEmbedMessage(event));
    }

    private MessageEmbed getEmbedMessage(CommandEvent event) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setFooter("Для активации: $activate")
                .setThumbnail(event.getGuild().getIconUrl())
                .addField("Список функций:", getActionListAsString(event), false)
                .build();
    }

    private String getActionListAsString(CommandEvent event) {
        ActionService service = ActionService.getInstance();
        List<ActionEntity> actions = service.getActions(event.getGuild().getId());

        return actions.stream()
                .map(action -> StringUtils.substringBefore(action.getAction(), "_event").toUpperCase() +
                        " : `" + action.isEnabled() + "`")
                .collect(Collectors.joining("\n"));
    }

}