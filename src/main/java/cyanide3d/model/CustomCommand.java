package cyanide3d.model;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.Objects;

public class CustomCommand extends Command {

    final String body;
    final String command;

    public CustomCommand(String name, String body) {
        this.name = name;
        this.body = body;
        this.command = name;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply(body);
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomCommand that = (CustomCommand) o;
        return Objects.equals(command, that.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }
}
