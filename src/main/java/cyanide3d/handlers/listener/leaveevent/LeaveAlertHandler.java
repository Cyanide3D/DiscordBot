package cyanide3d.handlers.listener.leaveevent;

import cyanide3d.service.ActionService;
import cyanide3d.service.ChannelService;
import cyanide3d.service.JoinLeaveService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

public class LeaveAlertHandler implements LeaveEventHandler {

    @Override
    public void execute(GuildMemberRemoveEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        ActionService actionService = ActionService.getInstance();
        if (!actionService.isActive(ActionType.LEAVE, event.getGuild().getId())){
            return;
        }

        JoinLeaveService service = JoinLeaveService.getInstance();
        MessageEmbed message = service.getEventMessage(ActionType.LEAVE, event.getGuild().getId(), event.getUser());

        channelService
                .getEventChannel(event.getJDA(), ActionType.LEAVE, event.getGuild().getId())
                .sendMessage(message)
                .queue();
    }
}


