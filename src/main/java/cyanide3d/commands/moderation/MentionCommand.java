package cyanide3d.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.PermissionService;
import cyanide3d.service.RoleService;
import cyanide3d.util.Permission;

public class MentionCommand extends Command {
    private final Localization localization = Localization.getInstance();

    public MentionCommand() {
        this.name = "mention";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        if (event.getArgs().isEmpty()) {
            event.reply(
                    "Необходимо указать дату.\n\n" +
                    "Пример: `$mention dd:mm:yyyy`"
            );
            return;
        }

        RoleService service = RoleService.getInstance();
        String message = service.getRoleList(event.getArgs(), event.getGuild().getId());

        event.reply(message);
    }
}
