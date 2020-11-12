package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.exceprtion.UnsupportedActionException;
import cyanide3d.exceprtion.UnsupportedStateException;
import cyanide3d.service.EnableActionService;


public class SupportCommand extends Command {
    public SupportCommand() {
        this.name = "sc";
        this.help = "sup";
        this.hidden = true;
    }


    @Override
    protected void execute(CommandEvent event) {
        try {
            EnableActionService.getInstance().setState("joinleave", "true");
        } catch (UnsupportedStateException e) {
            event.reply("state");
        } catch (UnsupportedActionException e) {
            event.reply("action");
        }
    }
}
