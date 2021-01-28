package cyanide3d.handlers.listener;

import cyanide3d.Localization;
import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.EnableActionService;
import cyanide3d.service.UserService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

import java.awt.*;

public class LeaveMemberHandler implements ListenerHandler {

    private final Localization localization = Localization.getInstance();
    private final GuildMemberRemoveEvent event;
    private final String gif;

    public LeaveMemberHandler(GuildMemberRemoveEvent event, String gif) {
        this.event = event;
        this.gif = gif;
    }

    @Override
    public void handle() {
        User user = event.getUser();
        UserService.getInstance().deleteUser(event.getUser().getId());
        EnableActionService enableActionService = EnableActionService.getInstance();
        if (!enableActionService.getState("joinleave")) {
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

        ChannelManagmentService.getInstance()
                .eventLeaveJoinChannel(event)
                .sendMessage(message)
                .queue();
    }
}
