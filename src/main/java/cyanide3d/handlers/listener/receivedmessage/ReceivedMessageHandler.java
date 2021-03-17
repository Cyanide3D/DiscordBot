package cyanide3d.handlers.listener.receivedmessage;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface ReceivedMessageHandler {
    void execute(GuildMessageReceivedEvent event);
}
