package cyanide3d.listener;

import cyanide3d.Localization;
import cyanide3d.actions.*;
import cyanide3d.conf.Logging;
import cyanide3d.conf.Permission;
import cyanide3d.model.Message;
import cyanide3d.service.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CyanoListener extends ListenerAdapter {

    Logger logger = Logging.getInstance().getLogger();

    private final String[] joinGifs = {"https://cdn.discordapp.com/attachments/573773778480398337/771325629491707924/tenor.gif",
            "https://cdn.discordapp.com/attachments/573773778480398337/771325641009135626/tenor_1.gif",
            "https://i.gifer.com/EIGB.gif",
            "https://i.gifer.com/D85T.gif",
            "https://i.gifer.com/Pvm.gif"};

    private final String[] leaveGifs = {"https://media.discordapp.net/attachments/614472783715500052/767371392466812938/tenor.gif.gif",
            "https://media.discordapp.net/attachments/614472783715500052/767371396354408458/good_bye_2.gif.gif",
            "https://i.gifer.com/53HC.gif", "https://i.gifer.com/9TEx.gif", "https://i.gifer.com/7A25.gif",
            "https://cdn.discordapp.com/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif"};

    private final Localization localization = Localization.getInstance();
    private final Random random;

    public CyanoListener() {
        random = new Random();
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        EnableActionService enableActionService = EnableActionService.getInstance();
        if (!enableActionService.getState("joinleave")){
            return;
        }
        User user = event.getUser();
        Role role = event.getGuild().getRolesByName("Гости", true).get(0);
        try {
            event.getGuild().addRoleToMember(user.getId(), role).queue();
        } catch (HierarchyException e){
            logger.log(Level.WARNING, "Failed add role in CyanoListener: \n", e);
        }

        MessageEmbed message = new EmbedBuilder()
                .setTitle(localization.getMessage("event.join.title"))
                .addField("",  user.getAsMention() + localization.getMessage("event.join.field"), false)
                .setThumbnail(user.getAvatarUrl())
                .setColor(Color.GREEN)
                .setAuthor(user.getAsTag(), user.getAvatarUrl(), user.getAvatarUrl())
                .setImage(getRandomGifUrl(joinGifs))
                .build();

        event.getUser().openPrivateChannel().queue(channel->
                channel.sendMessage(localization
                        .getMessage("privatemessage.join"))
                        .queue());
        ChannelManagmentService.getInstance()
                .eventLeaveJoinChannel(event)
                .sendMessage(message)
                .queue();
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        User user = event.getUser();
        UserService.getInstance().deleteUser(event.getUser().getId());
        EnableActionService enableActionService = EnableActionService.getInstance();
        if (!enableActionService.getState("joinleave")){
            return;
        }
        MessageEmbed message = new EmbedBuilder()
                .setTitle(localization.getMessage("event.leave.title"))
                .addField("", user.getAsMention() + localization.getMessage("event.leave.field"), false)
                .setThumbnail(user.getAvatarUrl())
                .setColor(Color.RED)
                .setAuthor(user.getAsTag(), user.getAvatarUrl(), user.getAvatarUrl())
                .setImage(getRandomGifUrl(leaveGifs))
                .build();

        ChannelManagmentService.getInstance()
                .eventLeaveJoinChannel(event)
                .sendMessage(message)
                .queue();
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Action action;
        if (!event.getAuthor().isBot())
            MessageCacheService.getInstance().add(new Message(event.getMessageId(),event.getMessage().getContentRaw()));
        ChannelManagmentService channels = ChannelManagmentService.getInstance();
        if (!event.getAuthor().isBot() && event.getChannel().equals(channels.blackListChannel(event))) {
            action = new BlacklistAddAction(event);
        } else if (!event.getAuthor().isBot() && event.getChannel().equals(channels.joinFormChannel(event)) && !PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            action = new JoinFormAction(event);
        } else {
            action = new SpeechFilterAction(event);
            if (!event.getAuthor().isBot()) new GainExpAction(event).execute();
            if (!event.getAuthor().isBot()) new AnswerAction(event).execute();
        }
        action.execute();
    }
    private String getRandomGifUrl(String[] gifs) {
        return gifs[random.nextInt(gifs.length)];
    }
}