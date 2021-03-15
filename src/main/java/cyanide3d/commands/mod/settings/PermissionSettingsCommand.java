package cyanide3d.commands.mod.settings;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.Role;

public class PermissionSettingsCommand extends Command {

    private final Localization localization = Localization.getInstance();
    PermissionService service = PermissionService.getInstance();

    public PermissionSettingsCommand() {
        this.name = "permission";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        final String[] args = event.getArgs().split(" ");
        final Role role = event.getMessage().getMentionedRoles().get(0);

        if (args.length < 2 || args.length > 3 || role == null) {
            event.reply("Ошибка, проверьте синтаксис команды.");
            return;
        }

        dispatch(args, role.getId(), event);
    }

    private void dispatch(String[] args, String role, CommandEvent event) {
        try {
            switch (args[0]) {
                case "add":
                    service.addRole(role,Permission.valueOf(args[2].toUpperCase()), event.getGuild().getId());
                    event.reply("Роль успешно наделена полномочиями!");
                    break;
                case "change":
                    service.changeRole(role, Permission.valueOf(args[2].toUpperCase()), event.getGuild().getId());
                    event.reply("Полномочия роли успешно изменены!");
                    break;
                case "delete":
                    service.removeRole(role, event.getGuild().getId());
                    event.reply("Полномочия c роли успешно сняты!");
                    break;
                default:
                    event.reply("Ошибка в синтаксисе команды.");
            }
        } catch (IllegalArgumentException e) {
            event.reply("Не корректное название привелегии.");
        }
    }

}
