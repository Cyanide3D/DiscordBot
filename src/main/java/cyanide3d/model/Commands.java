package cyanide3d.model;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Commands extends Command {

    String body;

    public Commands(String name, String body) {
        this.name = name;
        this.body = body;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply(body);
    }

    public String getBody() {
        return body;
    }

}
