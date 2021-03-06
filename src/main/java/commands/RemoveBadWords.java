package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import conf.DatabaseConnection;
import conf.Permission;
import conf.UserAccessToCommand;
import events.BadWordsEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class RemoveBadWords extends Command {

    ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

    public RemoveBadWords(){
        this.name = "removeword";
        this.aliases = new String[]{"removebadword"};
        this.arguments = "[word]";
        this.help = bundle.getString("removeword.help");
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        DatabaseConnection db = new DatabaseConnection();
        BadWordsEvent bwe = BadWordsEvent.getInstance();
        UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
        if(userAccess.getAccess(commandEvent.getMember(), Permission.MODERATOR)) {
            try {
                ArrayList<String> badWords = db.listBadWords();
                for (int i = 0; i < badWords.size(); i++) {
                    if (commandEvent.getArgs().equalsIgnoreCase(badWords.get(i))) {
                        db.removeBadWord(badWords.get(i));
                        commandEvent.reply(bundle.getString("removeword.successfully"));
                        bwe.setBadWords();
                        return;
                    }
                }
            } catch (SQLException throwables) {
                commandEvent.reply(bundle.getString("deniedAccessBD"));
            }
        }else{
            commandEvent.reply(String.format(bundle.getString("accessDenied"),this.name));
        }
    }
}
