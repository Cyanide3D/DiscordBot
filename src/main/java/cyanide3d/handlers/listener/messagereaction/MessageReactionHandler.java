package cyanide3d.handlers.listener.messagereaction;

import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;

public interface MessageReactionHandler {
    void execute(GenericGuildMessageReactionEvent event);
}
