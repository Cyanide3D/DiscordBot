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

        if (args.length < 2 || role == null) {
            event.reply("Ошибка, проверьте синтаксис команды.");
            return;
        }

        dispatch(args, role.getId(), event);
    }

    private Permission convertToPermission(String perm, CommandEvent event) {
        Permission permission;
        try {
            permission = Permission.valueOf(perm.toUpperCase());
        } catch (IllegalArgumentException e) {
            event.reply("Не корректное название привелегии.");
            permission = Permission.USER;
        }
        return permission;
    }

    private void dispatch(String[] args, String role, CommandEvent event) {
        switch (args[0]) {
//            case "list":
//                event.reply(listRolePermission(event));
//                break;
            case "add":
                service.addRole(role, convertToPermission(args[2], event), event.getGuild().getId());
                event.reply("Роль успешно наделена полномочиями!");
                break;
            case "change":
                service.changeRole(role, convertToPermission(args[2], event), event.getGuild().getId());
                event.reply("Полномочия роли успешно изменены!");
                break;
            case "delete":
                service.removeRole(role, event.getGuild().getId());
                event.reply("Полномочия c роли успешно сняты!");
                break;
            default:
                event.reply("Ошибка в синтаксисе команды.");
        }
    }

}
