package cyanide3d.handlers.listener.receivedmessage;

import cyanide3d.service.SpeechService;
import cyanide3d.service.ActionService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeechFilterHandler implements ReceivedMessageHandler {

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String messageText = event.getMessage().getContentStripped();
        ActionService actionService = ActionService.getInstance();
        if (!actionService.isActive(ActionType.SPEECH, event.getGuild().getId())){
            return;
        }
        if (event.getAuthor().isBot()) return;
        SpeechService speechService = SpeechService.getInstance();
        Pattern pattern = Pattern.compile("\\b[\\wа-яА-ЯёЁ]+\\b");
        Matcher matcher = pattern.matcher(messageText.toLowerCase());
        while (matcher.find()) {
            if (speechService.isBad(matcher.group(), event.getGuild().getId())) {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage("Не ругаться!").queue();
            }
        }
    }
}
