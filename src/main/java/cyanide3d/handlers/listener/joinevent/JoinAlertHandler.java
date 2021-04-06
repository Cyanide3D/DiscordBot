package cyanide3d.handlers.listener.joinevent;

import cyanide3d.repository.service.ActionService;
import cyanide3d.repository.service.ChannelService;
import cyanide3d.repository.service.JoinLeaveService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public class JoinAlertHandler implements JoinEventHandler {

    @Override
    public void execute(GuildMemberJoinEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        ActionService actionService = ActionService.getInstance();
        if (!actionService.isActive(ActionType.JOIN, event.getGuild().getId())) {
            return;
        }
        JoinLeaveService service = JoinLeaveService.getInstance();
        MessageEmbed message = service.getEventMessage(ActionType.JOIN, event.getGuild().getId(), event.getUser());

        channelService
                .getEventChannelOrDefault(ActionType.JOIN, event.getGuild().getId())
                .sendMessage(message)
                .queue();
    }

}
