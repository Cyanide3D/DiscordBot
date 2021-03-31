package cyanide3d.handlers.listener.receivedmessage;

import cyanide3d.repository.service.PunishmentService;
import cyanide3d.repository.service.SpeechService;
import cyanide3d.repository.service.ActionService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeechFilterHandler implements ReceivedMessageHandler {

    SpeechService speechService;
    ActionService actionService;
    PunishmentService punishmentService;

    public SpeechFilterHandler() {
        punishmentService = PunishmentService.getInstance();
        speechService = SpeechService.getInstance();
        actionService = ActionService.getInstance();
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String messageText = event.getMessage().getContentStripped();
        if (!actionService.isActive(ActionType.SPEECH, event.getGuild().getId()) || event.getAuthor().isBot()) {
            return;
        }
        Pattern pattern = Pattern.compile("\\b[\\wа-яА-ЯёЁ]+\\b");
        Matcher matcher = pattern.matcher(messageText.toLowerCase());
        while (matcher.find()) {
            if (speechService.isBadWord(matcher.group(), event.getGuild().getId())) {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage("Не ругаться!").queue();
                punishment(event);
            }
        }
    }

    private void punishment(GuildMessageReceivedEvent event) {
        try {
            punishmentService.increaseViolation(event.getGuild().getId(), event.getMember().getId());
        } catch (IllegalArgumentException ignored) {
        }
    }
}
