package cyanide3d.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.actions.MusicBotJoin;

public class Join extends Command {
    public Join() {
        this.name = "join";
    }

    @Override
    protected void execute(CommandEvent event) {
        new MusicBotJoin(event).join();
    }
}
