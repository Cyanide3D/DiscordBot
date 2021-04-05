package cyanide3d.handlers.listener.receivedmessage;

import cyanide3d.repository.service.MessageStoreService;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class MessageStoreHandler implements ReceivedMessageHandler{

    private final MessageStoreService storeService;

    public MessageStoreHandler() {
        this.storeService = MessageStoreService.getInstance();
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        String messageId = event.getMessageId();
        String messageBody = event.getMessage().getContentRaw();
        storeService.save(messageId, messageBody);
    }
}
