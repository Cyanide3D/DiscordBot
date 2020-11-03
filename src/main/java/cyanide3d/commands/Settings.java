package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.exceprtion.UnsupportedPermissionException;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.entities.Role;

import java.util.Locale;

public class Settings extends Command {

    PermissionService permissionService = PermissionService.getInstance();
    private Localization localization = new Localization(new Locale("ru", "RU"));

    public Settings() {
        this.name = "settings";
        this.aliases = new String[]{"changeperm"};
        this.arguments = "[subcommand] [action]";
        this.help = "Настройки полномочий.";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        String[] args = event.getArgs().split(" ");
        if (args.length >= 2 && args[0].equalsIgnoreCase("role")) {
            try {
                if (args[1].equalsIgnoreCase("list")) {
                    new ListRolePermissions().listRolePermission(event);
                    return;
                }
                Role mentionRole = event.getMessage().getMentionedRoles().get(0);
                if (args[1].equalsIgnoreCase("add")) {
                    permissionService.addRole(mentionRole, args[3]);
                    event.reply("Роль успешно добавлена в БД!");
                }
                if (args[1].equalsIgnoreCase("change")) {
                    permissionService.changeRole(mentionRole, args[3]);
                    event.reply("Полномочия роли успешно изменены!");
                }
                if (args[1].equalsIgnoreCase("delete")) {
                    permissionService.removeRole(mentionRole, args[3]);
                    event.reply("Полномочия c роли успешно сняты!");
                }
            } catch (UnsupportedPermissionException e) {
                event.reply(EmbedTemplates.SYNTAX_ERROR);
            } catch (IndexOutOfBoundsException e1){
                event.reply(EmbedTemplates.SYNTAX_ERROR);
            }
        } else {
            if (event.getArgs().length()==0) event.reply(EmbedTemplates.MENU);
        }
    }


}
