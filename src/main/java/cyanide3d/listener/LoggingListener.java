package cyanide3d.listener;

import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.EnableActionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.message.MessageBulkDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.self.SelfUpdateAvatarEvent;
import net.dv8tion.jda.api.events.self.SelfUpdateNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;

public class LoggingListener extends ListenerAdapter {

    EnableActionService enableActionService = EnableActionService.getInstance();

    public MessageEmbed makeMessage(String title, String event, String text, User user) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setAuthor(user.getAsTag(), null, user.getAvatarUrl())
                .setDescription(title + user.getAsMention())
                .addField(event, text, false)
                .setThumbnail(user.getEffectiveAvatarUrl())
                .setFooter("ID Пользователя: " + user.getId())
                .build();
    }

    @Override
    public void onUserUpdateAvatar(@Nonnull UserUpdateAvatarEvent event) {
        if (!enableActionService.getState("logging")){
            return;
        }
        String text = new StringBuilder()
                .append("[[До]](")
                .append(event.getOldAvatarUrl())
                .append(") -> [[После]](")
                .append(event.getNewAvatarUrl())
                .append(")")
                .toString();
        String title = "Изменение пользователя ";
        String action = "Аватарка";
        event.getUser().getMutualGuilds().stream().forEach(guild ->
                ChannelManagmentService.getInstance().loggingChannel(guild).sendMessage(makeMessage(title,action,text,event.getUser())).queue());
    }

    @Override
    public void onGuildMessageUpdate(@Nonnull GuildMessageUpdateEvent event) {
        if (!enableActionService.getState("logging")){
            return;
        }
        String text = event.getChannel().getAsMention() + " -> " + event.getMessage().getContentRaw();
        String title = "Пользователь ";
        String action = "Изменение сообщения";
        ChannelManagmentService.getInstance().loggingChannel(event.getGuild()).sendMessage(makeMessage(title,action,text,event.getAuthor())).queue();
    }

    @Override
    public void onUserUpdateName(@Nonnull UserUpdateNameEvent event) {
        if (!enableActionService.getState("logging")){
            return;
        }
        String text = new StringBuilder()
                .append("Старое имя профиля: ")
                .append(event.getOldName())
                .append("\n")
                .append("Новое имя профиля: ")
                .append(event.getNewName())
                .toString();
        String title = "Изменение пользователя ";
        String action = "Имя профиля";
        event.getUser().getMutualGuilds().stream().forEach(guild ->
                ChannelManagmentService.getInstance().loggingChannel(guild).sendMessage(makeMessage(title,action,text,event.getUser())).queue());
    }

    @Override
    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event) {
        if (!enableActionService.getState("logging")){
            return;
        }
        String text = new StringBuilder()
                .append("Старый никнейм: ")
                .append(event.getOldNickname())
                .append("\n")
                .append("Новый никнейм: ")
                .append(event.getNewNickname())
                .toString();
        String title = "Изменение пользователя ";
        String action = "Никнейм";
        ChannelManagmentService.getInstance().loggingChannel(event.getGuild()).sendMessage(makeMessage(title,action,text,event.getUser())).queue();
    }

    @Override
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {
        if (!enableActionService.getState("logging")){
            return;
        }
        String text = event.getRoles().get(0).getName();
        String title = "**Обновление пользователя** ";
        String action = "Добавлена роль";
        ChannelManagmentService.getInstance().loggingChannel(event.getGuild()).sendMessage(makeMessage(title,action,text,event.getUser())).queue();
    }

    @Override
    public void onGuildMemberRoleRemove(@Nonnull GuildMemberRoleRemoveEvent event) {
        if (!enableActionService.getState("logging")){
            return;
        }
        String text = event.getRoles().get(0).getName();
        String title = "**Обновление пользователя** ";
        String action = "Убрана роль";
        ChannelManagmentService.getInstance().loggingChannel(event.getGuild()).sendMessage(makeMessage(title,action,text,event.getUser())).queue();
    }
}
