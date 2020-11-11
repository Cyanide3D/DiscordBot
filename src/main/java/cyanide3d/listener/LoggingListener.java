package cyanide3d.listener;

import cyanide3d.service.ChannelManagmentService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.self.SelfUpdateAvatarEvent;
import net.dv8tion.jda.api.events.self.SelfUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;

public class LoggingListener extends ListenerAdapter {

    Guild guild;
    TextChannel parseChannel = ChannelManagmentService.getInstance().loggingChannel(guild);

    public MessageEmbed makeMessage(String title, String event, String text, User user) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setAuthor(user.getAsTag(), null, user.getAvatarUrl())
                .addField(title, "", false)
                .addField(event, text, false)
                .setFooter("ID Пользователя: " + user.getId())
                .build();
    }

    @Override
    public void onSelfUpdateAvatar(@Nonnull SelfUpdateAvatarEvent event) {

        String text = new StringBuilder()
                .append("[До](")
                .append(event.getOldAvatarUrl())
                .append(") -> [После](")
                .append(event.getNewAvatarUrl())
                .append(")")
                .toString();
        String title = "Изменение профиля пользователя!";
        //(makeMessage(title,"Аватарка",text,event.getSelfUser()));
    }

    @Override
    public void onGuildMessageDelete(@Nonnull GuildMessageDeleteEvent event) {
        //TODO
    }

    @Override
    public void onGuildMessageUpdate(@Nonnull GuildMessageUpdateEvent event) {
        //TODO
    }

    @Override
    public void onSelfUpdateName(@Nonnull SelfUpdateNameEvent event) {
        //TODO
    }
}
