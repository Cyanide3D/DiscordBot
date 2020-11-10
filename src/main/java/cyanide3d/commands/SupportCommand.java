package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.listener.CommandListener;


public class SupportCommand extends Command {
    public SupportCommand() {
        this.name = "sc";
        this.help = "sup";
        this.hidden = true;
    }


    @Override
    protected void execute(CommandEvent event) {
    }
}
