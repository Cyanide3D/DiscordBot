package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Role;

import java.io.*;

public class SupportCommand extends Command {
    public SupportCommand(){
        this.name = "sc";
        this.help = "sup";
        this.hidden = true;
    }



    @Override
    protected void execute(CommandEvent event) {
        event.reply("https://cdn.discordapp.com/attachments/691344546683944992/691368158065328158/Ox53c1jVqoE.jpg");
    }
}
