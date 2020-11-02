package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.conf.UserAccessToCommand;
import cyanide3d.service.BadWordsService;
import cyanide3d.conf.DatabaseConnection;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddBadWord extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public AddBadWord(){
        this.name = "addword";
        this.aliases = new String[]{"addbadword"};
        this.arguments = "[word]";
        this.help = localization.getMessage("addbadword.help");
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
        if(!userAccess.getAccess(commandEvent.getMember(), Permission.MODERATOR)) {
            commandEvent.reply(localization.getMessage("accessDenied",name));
            return;
        }
            try{
                new DatabaseConnection().addBadWords(commandEvent.getArgs());
                commandEvent.reply(localization.getMessage("addbadword.successfully"));
            } catch (SQLException throwables) {
                commandEvent.reply(localization.getMessage("deniedAccessBD"));
            }
    }
}
