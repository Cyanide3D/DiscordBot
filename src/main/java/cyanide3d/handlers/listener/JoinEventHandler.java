package cyanide3d.handlers.listener;

import cyanide3d.Localization;
import cyanide3d.service.ActionService;
import cyanide3d.service.ChannelService;
import cyanide3d.service.EntryMessageService;
import cyanide3d.service.JoinLeaveService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;

public class JoinEventHandler implements ListenerHandler {

    private final Localization localization = Localization.getInstance();
    Logger logger = LoggerFactory.getLogger(JoinEventHandler.class);
    private final String gif;
    private final GuildMemberJoinEvent event;

    public JoinEventHandler(String gif, GuildMemberJoinEvent event) {
        this.gif = gif;
        this.event = event;
    }

    @Override
    public void handle() {
        ChannelService channelService = ChannelService.getInstance();
        ActionService actionService = ActionService.getInstance();
        if (!actionService.isActive(ActionType.JOIN, event.getGuild().getId())) {
            return;
        }
        User user = event.getUser();
        if (actionService.isActive(ActionType.JOIN_MESSAGE, event.getGuild().getId())) {
            sendPrivateMessages(user, event.getGuild().getId());
        }

        JoinLeaveService service = JoinLeaveService.getInstance();
        MessageEmbed message = service.getEventMessage(ActionType.JOIN, event.getGuild().getId());

        channelService
                .getEventChannel(event.getJDA(), ActionType.JOIN, event.getGuild().getId())
                .sendMessage(message)
                .queue();
    }

    private void sendPrivateMessages(User user, String guildId) {
        EntryMessageService service = EntryMessageService.getInstance();
        final List<String> messages = service.getAllMessagesForGuild(guildId);

        for (String message : messages) {
            user.openPrivateChannel().queue(privateChannel ->
                    privateChannel.sendMessage(message).queue());
        }
    }
}
