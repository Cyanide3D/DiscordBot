package cyanide3d.handlers.listener;

import cyanide3d.Localization;
import cyanide3d.dto.ActionEntity;
import cyanide3d.service.ActionService;
import cyanide3d.service.ChannelService;
import cyanide3d.service.EntryMessageService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
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
        MessageEmbed message = new EmbedBuilder()
                .setTitle(localization.getMessage("event.join.title"))
                .addField("", user.getAsMention() + localization.getMessage("event.join.field"), false)
                .setThumbnail(user.getAvatarUrl())
                .setColor(Color.GREEN)
                .setAuthor(user.getAsTag(), user.getAvatarUrl(), user.getAvatarUrl())
                .setImage(gif)
                .build();

        channelService
                .getEventChannel(event.getJDA(), ActionType.JOIN, event.getGuild().getId())
                .sendMessage(message)
                .queue();

        sendPrivateMessages(user, event.getGuild().getId());
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
