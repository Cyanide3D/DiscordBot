package cyanide3d.handlers.listener;

import cyanide3d.Localization;
import cyanide3d.service.ChannelService;
import cyanide3d.service.ActionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

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
        ActionService actionService = ActionService.getInstance();
        if (!actionService.getState("joinleave")) {
            return;
        }
        User user = event.getUser();
        Role role = event.getGuild().getRolesByName("Гости", true).get(0);
        try {
            event.getGuild().addRoleToMember(user.getId(), role).queue();
        } catch (HierarchyException e) {
            logger.error("Failed add role in CyanoListener ", e);
        }

        MessageEmbed message = new EmbedBuilder()
                .setTitle(localization.getMessage("event.join.title"))
                .addField("", user.getAsMention() + localization.getMessage("event.join.field"), false)
                .setThumbnail(user.getAvatarUrl())
                .setColor(Color.GREEN)
                .setAuthor(user.getAsTag(), user.getAvatarUrl(), user.getAvatarUrl())
                .setImage(gif)
                .build();

        event.getUser().openPrivateChannel().queue(channel ->
                channel.sendMessage(localization
                        .getMessage("privatemessage.join"))
                        .queue());
        ChannelService.getInstance()
                .eventLeaveJoinChannel(event)
                .sendMessage(message)
                .queue();
    }
}