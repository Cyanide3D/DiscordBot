package cyanide3d.listener;

import cyanide3d.Localization;
import cyanide3d.actions.*;
import cyanide3d.conf.Logging;
import cyanide3d.conf.Permission;
import cyanide3d.handlers.FromDiscordToVkMessageHandler;
import cyanide3d.handlers.VacationHandler;
import cyanide3d.misc.MyGuild;
import cyanide3d.misc.TimerToPlayer;
import cyanide3d.model.Message;
import cyanide3d.model.RoleUse;
import cyanide3d.service.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
        if (!enableActionService.getState("joinleave")) {
            return;
        }
        User user = event.getUser();
        Role role = event.getGuild().getRolesByName("Гости", true).get(0);
        try {
            event.getGuild().addRoleToMember(user.getId(), role).queue();
        } catch (HierarchyException e) {
            logger.log(Level.WARNING, "Failed add role in CyanoListener: \n", e);
        }

        MessageEmbed message = new EmbedBuilder()
                .setTitle(localization.getMessage("event.join.title"))
                .addField("", user.getAsMention() + localization.getMessage("event.join.field"), false)
                .setThumbnail(user.getAvatarUrl())
                .setColor(Color.GREEN)
                .setAuthor(user.getAsTag(), user.getAvatarUrl(), user.getAvatarUrl())
                .setImage(getRandomGifUrl(joinGifs))
                .build();

        event.getUser().openPrivateChannel().queue(channel ->
                channel.sendMessage(localization
                        .getMessage("privatemessage.join"))
                        .queue());
        ChannelManagmentService.getInstance()
                .eventLeaveJoinChannel(event)
                .sendMessage(message)
                .queue();
    }

    @Override
    public void onGuildReady(@Nonnull GuildReadyEvent event) {
        TimerToPlayer.getInstance().setGuild(event.getGuild());
        MyGuild.getInstance().setGuild(event.getGuild());
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        PinService pinService = PinService.getInstance();
        List<Member> reactedUser = pinService.getReactedUser();
        List<String> pins = pinService.getPins();
        Member user = event.getMember();
        if (user.getUser().isBot() || reactedUser.contains(user) || pins.isEmpty()) {
            return;
        }
        if (event.retrieveMessage().complete().getAuthor().isBot() && event.getReaction().retrieveUsers().complete().stream().filter(user1 -> user1.isBot()).findAny() != null && pinService.getParseMessage().getId().equalsIgnoreCase(event.getMessageId())) {
            String message = pins.get(pins.size() - 1);
            user.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(message).queue());
            ChannelManagmentService.getInstance().loggingChannel(event.getGuild()).sendMessage(user.getUser().getAsMention() + " взял пин " + pins.get(pins.size() - 1)).queue();
            pinService.setReactedUser(user);
            pinService.removePin(pins.size() - 1);
            if (pins.isEmpty()) {
                if (pinService.getParseMessage() != null) {
                    pinService.getParseMessage().delete().queue();
                    pinService.setParseMessage(null);
                }
            }
        }
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
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
                .setImage(getRandomGifUrl(leaveGifs))
                .build();

        ChannelManagmentService.getInstance()
                .eventLeaveJoinChannel(event)
                .sendMessage(message)
                .queue();
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Action action;
        if (!event.getAuthor().isBot()) {
            mentionCacheHandler(event);
        }

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

        if (event.getChannel().getId().equals("791636377145180191") && !event.getAuthor().isBot()){
            sendToVkHandler(event);
        }

        if (event.getChannel().getId().equals("785133010990792764") && !event.getAuthor().isBot()){
            new VacationHandler(event);
        }

    }

    private void mentionCacheHandler(GuildMessageReceivedEvent event) {
        MessageCacheService.getInstance().add(new Message(event.getMessageId(), event.getMessage().getContentRaw()));
        List<Role> roles = event.getMessage().getMentionedRoles();
        if (!roles.isEmpty()) {
            for (Role role : roles) {
                MessageCacheService.getInstance().add(new RoleUse(role.getId(), new SimpleDateFormat("dd:MM:yyyy").format(new Date()), "1"));
            }
        }
    }

    private void sendToVkHandler(GuildMessageReceivedEvent event) {
        StringBuilder message = new StringBuilder()
                .append(event.getMessage().getContentRaw());
        List<net.dv8tion.jda.api.entities.Message.Attachment> attachments = event.getMessage().getAttachments();
        if (!attachments.isEmpty()){
            for (net.dv8tion.jda.api.entities.Message.Attachment attachment : attachments) {
                message
                        .append(" ")
                        .append(attachment.getUrl());
            }
        }
        new FromDiscordToVkMessageHandler().send(event.getMember().getNickname() + ":" + message.toString());
    }

    private String getRandomGifUrl(String[] gifs) {
        return gifs[random.nextInt(gifs.length)];
    }

}