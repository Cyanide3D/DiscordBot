package cyanide3d.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;

import java.util.Random;

public class Facts extends Command {
    private final Localization localization = Localization.getInstance();
    final String[] facts = {localization.getMessage("warface.facts.1"),
            localization.getMessage("warface.facts.2"),
            localization.getMessage("warface.facts.3"),
            localization.getMessage("warface.facts.4"),
            localization.getMessage("warface.facts.5"),
            localization.getMessage("warface.facts.6"),
            localization.getMessage("warface.facts.7"),
            localization.getMessage("warface.facts.8"),
            localization.getMessage("warface.facts.9"),
            localization.getMessage("warface.facts.10"),
            localization.getMessage("warface.facts.11"),
            localization.getMessage("warface.facts.12"),
            localization.getMessage("warface.facts.13"),
            localization.getMessage("warface.facts.14"),
            localization.getMessage("warface.facts.15"),
            localization.getMessage("warface.facts.16"),
            localization.getMessage("warface.facts.17"),
            localization.getMessage("warface.facts.18"),
            localization.getMessage("warface.facts.19"),
            localization.getMessage("warface.facts.20"),
            localization.getMessage("warface.facts.21"),
            localization.getMessage("warface.facts.22"),
            localization.getMessage("warface.facts.23"),
            localization.getMessage("warface.facts.24"),
            localization.getMessage("warface.facts.25"),
            localization.getMessage("warface.facts.26"),
            localization.getMessage("warface.facts.27"),
            localization.getMessage("warface.facts.28"),
            localization.getMessage("warface.facts.29")};

    public Facts(){
        this.name = "fact";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply(facts[new Random().nextInt(facts.length)]);
    }
}
