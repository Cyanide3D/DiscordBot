package cyanide3d.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;

import java.util.Random;

public class EightBall extends Command {
    private final Localization localization = Localization.getInstance();

    public EightBall() {
        this.name = "8ball";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()){
            event.reply("Вы должны что то спросить у шара.");
            return;
        }
        String[] answer = localization.getMessage("8ball").split("\n");
        event.reply(answer[new Random().nextInt(answer.length)]);
    }
}
