package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Logging;

import java.util.logging.Logger;

public class SupportCommand extends Command {
    Logger logger = Logging.getInstance().getLogger();

    public SupportCommand() {
        this.name = "sc";
        this.help = "sup";
        this.hidden = true;
    }


    @Override
    protected void execute(CommandEvent event) {
        logger.warning("sdsdfsdf");
    }
}
