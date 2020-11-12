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
            EnableActionService.getInstance().setState("logging", "true");
        } catch (UnsupportedStateException e) {
            e.printStackTrace();
        } catch (UnsupportedActionException e) {
            e.printStackTrace();
        }
    }
}
