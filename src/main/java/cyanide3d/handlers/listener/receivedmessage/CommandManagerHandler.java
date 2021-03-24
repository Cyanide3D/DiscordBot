package cyanide3d.handlers.listener.receivedmessage;

import cyanide3d.listener.CommandClientManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class CommandManagerHandler implements ReceivedMessageHandler{
    @Override
    public void execute(GuildMessageReceivedEvent event) {
        if (!StringUtils.startsWith(event.getMessage().getContentRaw(), "$") && event.getAuthor().isBot())
            return;

        CommandClientManager.getInstance().registryCommandManager(event.getGuild().getId());
    }
}
