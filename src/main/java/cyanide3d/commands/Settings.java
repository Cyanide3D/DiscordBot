package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Permission;
import cyanide3d.conf.UserAccessToCommand;

import java.util.Locale;
import java.util.ResourceBundle;

public class Settings extends Command {

    RolesSettings rolesSet = new RolesSettings();
    EmbedSettings embed = new EmbedSettings();
    //ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

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

            if (args.length >= 2) {
                if (args[0].equalsIgnoreCase("role") && args[1].equalsIgnoreCase("add"))
                    rolesSet.addRolePermission(e, args);
                if (args[0].equalsIgnoreCase("role") && args[1].equalsIgnoreCase("list"))
                    rolesSet.listRolePermission(e);
                if (args[0].equalsIgnoreCase("role") && args[1].equalsIgnoreCase("change"))
                    rolesSet.changeRolePermission(e, args);
                if (args[0].equalsIgnoreCase("role") && args[1].equalsIgnoreCase("delete"))
                    rolesSet.deleteRolePermission(e, args);
            } else {
                e.reply(String.valueOf(args.length));
                if (args.length == 1)
                    embed.embedSettingsMenu(e);
            }
        }else{
            e.reply("accessDenied");
        }
    }


}
