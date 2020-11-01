package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import conf.Permission;
import conf.UserAccessToCommand;
import events.BadWordsEvent;
import net.dv8tion.jda.api.entities.User;

public class SupportCommand extends Command {
    public SupportCommand(){
        this.name = "sc";
        this.help = "sup";
        this.hidden = true;
    }
    @Override
    protected void execute(CommandEvent e) {
        UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
            userAccess.setRolesIDs();
            BadWordsEvent bwe = BadWordsEvent.getInstance();
            bwe.setBadWords();
    }
}
