package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Permission;
import cyanide3d.conf.UserAccessToCommand;
import cyanide3d.events.BadWordsEvent;
import cyanide3d.conf.DatabaseConnection;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddBadWord extends Command {

    ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

    public AddBadWord(){
        this.name = "addword";
        this.aliases = new String[]{"addbadword"};
        this.arguments = "[word]";
        this.help = bundle.getString("addbadword.help");
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
        if(userAccess.getAccess(commandEvent.getMember(), Permission.MODERATOR)) {
            DatabaseConnection db = new DatabaseConnection();
            BadWordsEvent bwe = BadWordsEvent.getInstance();
            try {
                db.addBadWords(commandEvent.getArgs());
                commandEvent.reply(bundle.getString("addbadword.successfully"));
                bwe.setBadWords();
            } catch (SQLException throwables) {
                commandEvent.reply(bundle.getString("deniedAccessBD"));
            }
        }else{
            commandEvent.reply(String.format(bundle.getString("accessDenied"),this.name));
        }
    }
}