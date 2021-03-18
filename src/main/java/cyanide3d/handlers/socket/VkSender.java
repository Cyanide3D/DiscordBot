package cyanide3d.handlers.socket;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class VkSender {

    private final GuildMessageReceivedEvent event;
    private final Guild guild;

    public VkSender(GuildMessageReceivedEvent event, Guild guild) {
        this.event = event;
        this.guild = guild;
    }

    public void handle() {
        StringBuilder message = new StringBuilder()
                .append(event.getMessage().getContentRaw());
        List<Message.Attachment> attachments = event.getMessage().getAttachments();
        if (!attachments.isEmpty()){
            for (net.dv8tion.jda.api.entities.Message.Attachment attachment : attachments) {
                message
                        .append(" ")
                        .append(attachment.getUrl());
            }
        }
        new VkSocketSender().send(event.getMember().getNickname() + ":" + message.toString(), guild);
    }
}
