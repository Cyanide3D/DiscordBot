package cyanide3d.actions;

import cyanide3d.service.BadWordsService;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeechFilterAction implements Action {

    private final GuildMessageReceivedEvent event;
    private String messageText;

    public SpeechFilterAction(GuildMessageReceivedEvent event) {
        this.event = event;
        messageText = event.getMessage().getContentStripped();
    }

    @Override
    public void execute() {
        BadWordsService badWordsService = BadWordsService.getInstance();
        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(messageText);
        while (matcher.find()) {
            if (badWordsService.isBad(matcher.group())) {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage("Не ругаться!").queue();
            }
        }
    }
}
