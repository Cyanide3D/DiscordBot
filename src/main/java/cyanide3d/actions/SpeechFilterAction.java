package cyanide3d.actions;

import cyanide3d.dto.ActionEntity;
import cyanide3d.dto.BadwordEntity;
import cyanide3d.service.SpeechService;
import cyanide3d.service.ActionService;
import cyanide3d.util.ActionType;
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
        ActionService actionService = new ActionService(ActionEntity.class, event.getGuild().getId());
        if (!actionService.isActive(ActionType.SPEECH)){
            return;
        }
        if (event.getAuthor().isBot()) return;
        SpeechService speechService = new SpeechService(BadwordEntity.class, event.getGuild().getId());
        Pattern pattern = Pattern.compile("\\b[\\wа-яА-ЯёЁ]+\\b");
        Matcher matcher = pattern.matcher(messageText.toLowerCase());
        while (matcher.find()) {
            if (speechService.isBad(matcher.group())) {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage("Не ругаться!").queue();
            }
        }
    }
}
