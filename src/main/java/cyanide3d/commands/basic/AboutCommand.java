package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.Configuration;


public class AboutCommand extends Command {
    Configuration configuration;
    Localization localization;

    public AboutCommand() {
        this.name = "about";
        localization = Localization.getInstance();
        configuration = Configuration.getInstance();
    }

    @Override
    protected void execute(CommandEvent e) {
        e.reply(localization.getMessage("about.text", configuration.getOwner()));
    }
}