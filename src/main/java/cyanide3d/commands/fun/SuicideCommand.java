package cyanide3d.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;

import java.util.Random;

public class SuicideCommand extends Command {
    private final String[] reasonDie;
    private final Localization localization;

    public SuicideCommand(){
        this.name = "suicide";
        localization = Localization.getInstance();
        reasonDie = localization.getMessage("command.suicide.text").split("\n");
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply(event.getAuthor().getAsMention() + " " + reasonDie[new Random().nextInt(reasonDie.length)]);
    }
}
