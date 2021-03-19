package cyanide3d.handlers.listener.receivedmessage.dialog;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class DefaultInterceptor implements MessageInterceptor{
    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (!event.getAuthor().isBot() && message.contains("бот") && message.contains("лох")) {
            event.getChannel().sendMessage(".i. Соси жопу мудак.").queue();
        }
        if (!event.getAuthor().isBot() && message.contains("кек")) {
            event.getChannel().sendMessage("Тише будь, ишь расКЕКался тут.").queue();
        }
    }
}
