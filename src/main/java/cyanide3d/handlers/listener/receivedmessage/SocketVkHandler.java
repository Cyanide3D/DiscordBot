package cyanide3d.handlers.listener.receivedmessage;

import cyanide3d.handlers.socket.VkSender;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SocketVkHandler implements ReceivedMessageHandler {

    private final Guild guild;

    public SocketVkHandler(JDA jda) {
        guild = jda.getGuildById("664813726981160961");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        if (!event.getChannel().getId().equals("791636377145180191") || event.getAuthor().isBot()) {
            return;
        }

        new VkSender(event, guild).handle();
    }
}
