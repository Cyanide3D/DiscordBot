package cyanide3d.handlers.listener;

import cyanide3d.handlers.socket.FromDiscordToVkMessageHandler;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class SendToVkHandler implements ListenerHandler {

    private final GuildMessageReceivedEvent event;

    public SendToVkHandler(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    @Override
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
        new FromDiscordToVkMessageHandler().send(event.getMember().getNickname() + ":" + message.toString());
    }
}