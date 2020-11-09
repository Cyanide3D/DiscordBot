package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Bot;
import cyanide3d.conf.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.sharding.DefaultShardManager;

import java.io.IOException;


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
