package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Logging;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.nio.charset.Charset;
import java.util.logging.Logger;

public class SupportCommand extends Command {

    public SupportCommand() {
        this.name = "sc";
    }


    @Override
    protected void execute(CommandEvent event) {
        if (!event.getAuthor().getId().equals("534894366448156682")){
            return;
        }
        event.getChannel().sendMessage("WORK").queue();
    }
}
