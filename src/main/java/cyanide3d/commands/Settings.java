package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.conf.UserAccessToCommand;
import cyanide3d.exceprtion.UnsupportedPermissionException;
import cyanide3d.service.PermissionService;

import java.util.Locale;

public class Settings extends Command {

    RolesSettings rolesSet = new RolesSettings();
    PermissionService permissionService = PermissionService.getInstance();
    EmbedTemplates embed = new EmbedTemplates();
    private Localization localization = new Localization(new Locale("ru", "RU"));

    public Settings() {
        this.name = "settings";
        this.aliases = new String[]{"changeperm"};
        this.arguments = "[subcommand] [action]";
        this.help = "Настройки полномочий.";
    }

    @Override
    protected void execute(CommandEvent event) {
        UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
        if (!userAccess.getAccess(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        String[] args = event.getArgs().split(" ");
        if (args.length >= 2 && args[0].equalsIgnoreCase("role")) {
            try {
                if (args[1].equalsIgnoreCase("add"))
                    permissionService.addRole(args[2], args[3]);
                rolesSet.addRolePermission(event, args);
                if (args[1].equalsIgnoreCase("list"))
                    rolesSet.listRolePermission(event);
                if (args[1].equalsIgnoreCase("change"))
                    rolesSet.changeRolePermission(event, args);
                if (args[1].equalsIgnoreCase("delete"))
                    rolesSet.deleteRolePermission(event, args);
            } catch (UnsupportedPermissionException e) {
                event.reply(localization.getMessage("roles.fail"));
            }
        } else {
            event.reply(String.valueOf(args.length));
            if (args.length == 1)
                event.reply(EmbedTemplates.MENU);
        }
    }


}
