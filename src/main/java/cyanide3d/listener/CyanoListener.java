package cyanide3d.listener;

import cyanide3d.Localization;
import cyanide3d.actions.Action;
import cyanide3d.actions.BlacklistAddAction;
import cyanide3d.actions.JoinFormAction;
import cyanide3d.actions.SpeechFilterAction;
import cyanide3d.conf.ChannelManagment;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Locale;
import java.util.Random;

public class CyanoListener extends ListenerAdapter {
    private final String[] joinGifs = {"https://cdn.discordapp.com/attachments/573773778480398337/771325629491707924/tenor.gif",
            "https://cdn.discordapp.com/attachments/573773778480398337/771325641009135626/tenor_1.gif",
            "https://i.gifer.com/EIGB.gif",
            "https://i.gifer.com/D85T.gif",
            "https://i.gifer.com/Pvm.gif"};

    private final String[] leaveGifs = {"https://media.discordapp.net/attachments/614472783715500052/767371392466812938/tenor.gif.gif",
            "https://media.discordapp.net/attachments/614472783715500052/767371396354408458/good_bye_2.gif.gif",
            "https://i.gifer.com/53HC.gif", "https://i.gifer.com/9TEx.gif", "https://i.gifer.com/7A25.gif",
            "https://cdn.discordapp.com/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif"};

    private Localization localization = new Localization(new Locale("ru", "RU"));
    private final Random random;

    public CyanoListener() {
        random = new Random();
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        User user = event.getUser();
        Role role = event.getGuild().getRolesByName("Гости", true).get(0);
        event.getGuild().addRoleToMember(user.getId(), role).queue();

        MessageEmbed message = new EmbedBuilder()
                .setTitle(localization.getMessage("event.join.title"))
                .addField("", localization.getMessage("event.join.field", user.getAsMention()), false)
                .setThumbnail(user.getAvatarUrl())
                .setColor(Color.GREEN)
                .setAuthor(user.getAsTag(), user.getAvatarUrl(), user.getAvatarUrl())
                .setImage(getRandomGifUrl(joinGifs))
                .build();

        ChannelManagment.getInstance()
                .eventLeaveJoinChannel(event)
                .sendMessage(message)
                .queue();
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        User user = event.getUser();

        MessageEmbed message = new EmbedBuilder()
                .setTitle(localization.getMessage("event.leave.title"))
                .addField("", localization.getMessage("event.leave.field", user.getAsMention()), false)
                .setThumbnail(user.getAvatarUrl())
                .setColor(Color.RED)
                .setAuthor(user.getAsTag(), user.getAvatarUrl(), user.getAvatarUrl())
                .setImage(getRandomGifUrl(leaveGifs))
                .build();

        ChannelManagment.getInstance()
                .eventLeaveJoinChannel(event)
                .sendMessage(message)
                .queue();
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Action action;

        if (event.getChannel().getId().equalsIgnoreCase("664823753116745758")) {//TODO remove hardcode
            action = new BlacklistAddAction(event);
        } else if (false) {//TODO condition
            action = new JoinFormAction(event);
        } else {
            action = new SpeechFilterAction(event);
        }

        action.execute();
    }

    private String getRandomGifUrl(String[] gifs) {
        return gifs[random.nextInt(gifs.length)];
    }
}
