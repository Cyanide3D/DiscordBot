package cyanide3d.handlers.listener.leaveevent;

import cyanide3d.handlers.listener.leaveevent.LeaveEventHandler;
import cyanide3d.service.ActionService;
import cyanide3d.service.ChannelService;
import cyanide3d.service.JoinLeaveService;
import cyanide3d.service.UserService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

public class LeaveAlertHandler implements LeaveEventHandler {

    @Override
    public void execute(GuildMemberRemoveEvent event) {
        User user = event.getUser();
        UserService.getInstance()
                .deleteUser(user.getId(), event.getGuild().getId());
        ChannelService channelService = ChannelService.getInstance();
        ActionService actionService = ActionService.getInstance();
        if (!actionService.isActive(ActionType.LEAVE, event.getGuild().getId())){
            return;
        }

        JoinLeaveService service = JoinLeaveService.getInstance();
        MessageEmbed message = service.getEventMessage(ActionType.LEAVE, event.getGuild().getId());

        channelService
                .getEventChannel(event.getJDA(), ActionType.LEAVE, event.getGuild().getId())
                .sendMessage(message)
                .queue();
    }
}

