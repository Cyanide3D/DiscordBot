package cyanide3d.listener;

import cyanide3d.repository.service.ChannelService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class LoggingMessageSender {

    private final ChannelService channelService;

    public LoggingMessageSender() {
        channelService = ChannelService.getInstance();
    }

    public void sendGuildChangeMessage(String title, String action, String text, Guild guild) {
        getTextChannel(guild).sendMessage(makeMessageGuildChange(title, action, text, guild)).queue();
    }

    public void sendUserChangeMessage(String title, String action, String text, Guild guild, User user) {
        getTextChannel(guild).sendMessage(makeMessageUserChange(title, action, text, user)).queue();
    }

    private TextChannel getTextChannel(Guild guild) {
        return channelService.getEventChannel(guild.getJDA(), ActionType.LOG, guild.getId());
    }

    private MessageEmbed makeMessageUserChange(String title, String event, String text, User user) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setAuthor(user.getAsTag(), null, user.getAvatarUrl())
                .setDescription(title + user.getAsMention())
                .addField(event, text, false)
                .setThumbnail(user.getEffectiveAvatarUrl())
                .setFooter("ID Пользователя: " + user.getId())
                .build();
    }

    private MessageEmbed makeMessageGuildChange(String title, String event, String text, Guild guild) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setDescription(title)
                .addField(event, text, false)
                .setThumbnail(guild.getIconUrl())
                .setFooter("ID Сервера: " + guild.getId())
                .build();
    }
}
