package cyanide3d.handlers.listener;

import cyanide3d.Localization;
import cyanide3d.dto.ActionEntity;
import cyanide3d.service.ActionService;
import cyanide3d.service.ChannelService;
import cyanide3d.service.UserService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

import java.awt.*;

public class LeaveEventHandler implements ListenerHandler {

    private final Localization localization = Localization.getInstance();
    private final GuildMemberRemoveEvent event;
    private final String gif;

    public LeaveEventHandler(GuildMemberRemoveEvent event, String gif) {
        this.event = event;
        this.gif = gif;
    }

    @Override
    public void handle() {
        User user = event.getUser();
        UserService.getInstance()
                .deleteUser(user.getId(), event.getGuild().getId());
        ChannelService channelService = ChannelService.getInstance();
        ActionService actionService = ActionService.getInstance();
        if (!actionService.isActive(ActionType.LEAVE, event.getGuild().getId())){
            return;
        }
        MessageEmbed message = new EmbedBuilder()
                .setTitle(localization.getMessage("event.leave.title"))
                .addField("", user.getAsMention() + localization.getMessage("event.leave.field"), false)
                .setThumbnail(user.getAvatarUrl())
                .setColor(Color.RED)
                .setAuthor(user.getAsTag(), user.getAvatarUrl(), user.getAvatarUrl())
                .setImage(gif)
                .build();

        channelService
                .getEventChannel(event.getJDA(), ActionType.LEAVE, event.getGuild().getId())
                .sendMessage(message)
                .queue();
    }
}
