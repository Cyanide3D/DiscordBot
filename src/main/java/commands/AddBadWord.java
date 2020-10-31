package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import conf.Permission;
import events.BadWordsEvent;
import net.dv8tion.jda.api.entities.TextChannel;
import conf.DatabaseConnection;
import conf.UserAcessToCommand;

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
        BadWordsEvent badWE = BadWordsEvent.getInstance();
        DatabaseConnection db = new DatabaseConnection();
        UserAcessToCommand usrAccess = UserAcessToCommand.getInstance();
        if(usrAccess.getAccess(commandEvent.getAuthor().getId(), Permission.MODERATOR)) {
            try {
                db.addBadWords(commandEvent.getArgs());
                commandEvent.reply(bundle.getString("addbadword.successfully"));
                badWE.setBadWords();
            } catch (SQLException throwables) {
                commandEvent.reply(bundle.getString("deniedAccessBD"));
            }
        }else{
            commandEvent.reply(String.format(bundle.getString("accessDenied"),this.name));
        }
    }
}
