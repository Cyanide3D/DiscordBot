package cyanide3d.handlers.listener.joinevent;

import cyanide3d.repository.service.ActionService;
import cyanide3d.repository.service.GreetingService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import java.util.List;

public class GreetingHandler implements JoinEventHandler{
    @Override
    public void execute(GuildMemberJoinEvent event) {
        ActionService actionService = ActionService.getInstance();
        if (!actionService.isActive(ActionType.JOIN_MESSAGE, event.getGuild().getId())) {
            return;
        }
        GreetingService service = GreetingService.getInstance();
        final List<String> messages = service.getAllMessagesForGuild(event.getGuild().getId());

        for (String message : messages) {
            event.getUser().openPrivateChannel().queue(privateChannel ->
                    privateChannel.sendMessage(message).queue());
        }
    }
}
