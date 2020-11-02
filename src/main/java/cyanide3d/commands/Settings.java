package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.conf.UserAccessToCommand;

import java.util.Locale;

public class Settings extends Command {

    RolesSettings rolesSet = new RolesSettings();
    EmbedTemplates embed = new EmbedTemplates();
    private Localization localization = new Localization(new Locale("ru", "RU"));

    public Settings() {
        this.name = "settings";
        this.aliases = new String[]{"changeperm"};
        this.arguments = "[subcommand] [action]";
        this.help = "Настройки полномочий.";
    }

    @Override
    protected void execute(CommandEvent e) {
        UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
        if(userAccess.getAccess(e.getMember(), Permission.MODERATOR)) {
            String[] args = e.getArgs().split(" ");

            if (args.length >= 2 && args[0].equalsIgnoreCase("role")) {
                //TODO switch
                if (args[1].equalsIgnoreCase("add"))
                    rolesSet.addRolePermission(e, args);
                if (args[1].equalsIgnoreCase("list"))
                    rolesSet.listRolePermission(e);
                if (args[1].equalsIgnoreCase("change"))
                    rolesSet.changeRolePermission(e, args);
                if (args[1].equalsIgnoreCase("delete"))
                    rolesSet.deleteRolePermission(e, args);
            } else {
                e.reply(String.valueOf(args.length));
                if (args.length == 1)
                    e.reply(EmbedTemplates.MENU);
            }
        }else{
            e.reply(localization.getMessage("accessDenied",name));
        }
    }


}
