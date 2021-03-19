package cyanide3d.handlers.listener.receivedmessage.dialog;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface MessageInterceptor {
    void execute(GuildMessageReceivedEvent event);
}
