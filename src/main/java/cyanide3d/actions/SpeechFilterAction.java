package cyanide3d.actions;

import cyanide3d.service.BadWordsService;
import cyanide3d.service.EnableActionService;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeechFilterAction implements Action {

    private final GuildMessageReceivedEvent event;
    private final String messageText;

    public SpeechFilterAction(GuildMessageReceivedEvent event) {
        this.event = event;
        messageText = event.getMessage().getContentStripped();
    }

    @Override
    public void execute() {
        EnableActionService enableActionService = EnableActionService.getInstance();
        if (!enableActionService.getState("speechfilter")){
            return;
        }
        if (event.getAuthor().isBot()) return;
        BadWordsService badWordsService = BadWordsService.getInstance();
        Pattern pattern = Pattern.compile("\\b[\\wа-яА-ЯёЁ]+\\b");
        Matcher matcher = pattern.matcher(messageText.toLowerCase());
        while (matcher.find()) {
            if (badWordsService.isBad(matcher.group())) {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage("Не ругаться!").queue();
            }
        }
    }
}
