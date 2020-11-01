package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.UserAccessToCommand;
import cyanide3d.events.BadWordsEvent;

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
