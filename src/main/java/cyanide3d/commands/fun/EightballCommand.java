package cyanide3d.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;

import java.util.Random;

public class EightballCommand extends Command {
    private final Localization localization = Localization.getInstance();

    public EightballCommand() {
        this.name = "8ball";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()){
            event.reply(localization.getMessage("command.8ball.noargs"));
            return;
        }
        String[] answer = localization.getMessage("command.8ball.text").split("\n");
        event.reply(answer[new Random().nextInt(answer.length)]);
    }
}
