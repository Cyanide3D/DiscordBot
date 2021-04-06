package cyanide3d.handlers.listener.receivedmessage.dialog;

import cyanide3d.Localization;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class DefaultInterceptor implements MessageInterceptor{

    private final Localization localization;

    public DefaultInterceptor() {
        localization = Localization.getInstance();
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        String message = parseMessage(event);
        if (!message.isEmpty()) {
            event.getChannel().sendMessage(message).queue();
        }
    }

    private String parseMessage(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String parseMessage = "";
        if (message.equalsIgnoreCase("бот лох")) {
            parseMessage = ".i. Соси жопу мудак.";
        } else if (message.contains("кек")) {
            parseMessage = "Тише будь, ишь расКЕКался тут.";
        } else if (message.equalsIgnoreCase("кто я?")) {
            String[] answers = localization.getMessage("dialog.whoisiam").split("\n");
            parseMessage = answers[new Random().nextInt(answers.length)];
        }
        return parseMessage;
    }
}
