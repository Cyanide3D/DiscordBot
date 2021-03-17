package cyanide3d.handlers.listener.joinevent;

import cyanide3d.handlers.listener.joinevent.JoinEventHandler;
import cyanide3d.service.ActionService;
import cyanide3d.service.ChannelService;
import cyanide3d.service.EntryMessageService;
import cyanide3d.service.JoinLeaveService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import java.util.List;

public class JoinAlertHandler implements JoinEventHandler {

    @Override
    public void execute(GuildMemberJoinEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        ActionService actionService = ActionService.getInstance();
        if (!actionService.isActive(ActionType.JOIN, event.getGuild().getId())) {
            return;
        }
        JoinLeaveService service = JoinLeaveService.getInstance();
        MessageEmbed message = service.getEventMessage(ActionType.JOIN, event.getGuild().getId());

        channelService
                .getEventChannel(event.getJDA(), ActionType.JOIN, event.getGuild().getId())
                .sendMessage(message)
                .queue();
    }

}