package cyanide3d.listener;

import cyanide3d.repository.service.ActionService;
import cyanide3d.repository.service.ChannelService;
import cyanide3d.repository.service.MessageStoreService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateIconEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;

public class LogListener extends ListenerAdapter {

    private final LoggingMessageSender messageSender;

    public LogListener() {
        messageSender = new LoggingMessageSender();
    }


    @Override
    public void onGuildMessageDelete(@Nonnull GuildMessageDeleteEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        MessageStoreService storeService = MessageStoreService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Удаление сообщения";
        String text = event.getChannel().getAsMention() + " -> " + storeService.getMessageBodyById(event.getMessageId());
        storeService.delete(event.getMessageId());

        messageSender.sendGuildChangeMessage(title, action, text, event.getGuild());
    }

    @Override
    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String text = "Старый никнейм: " + event.getOldNickname() + "\nНовый никнейм: " + event.getNewNickname();
        String title = "**Изменение пользователя** ";
        String action = "Никнейм";

        messageSender.sendUserChangeMessage(title, action, text, event.getGuild(), event.getUser());
    }

    @Override
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String text = event.getRoles().get(0).getName();
        String title = "**Обновление пользователя** ";
        String action = "Добавлена роль";

        messageSender.sendUserChangeMessage(title, action, text, event.getGuild(), event.getUser());
    }

    @Override
    public void onGuildMemberRoleRemove(@Nonnull GuildMemberRoleRemoveEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String text = event.getRoles().get(0).getName();
        String title = "**Обновление пользователя** ";
        String action = "Убрана роль";

        messageSender.sendUserChangeMessage(title, action, text, event.getGuild(), event.getUser());
    }

    @Override
    public void onGuildInviteCreate(@Nonnull GuildInviteCreateEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String title = "**Обновление сервера** ";
        String action = "Cоздан инвайт";
        String text = "Автор инвайта: " + event.getInvite().getInviter().getName() + "\n"
                + "Ссылка на инвайт: " + event.getInvite().getUrl();

        messageSender.sendGuildChangeMessage(title, action, text, event.getGuild());
    }

    @Override
    public void onRoleCreate(@Nonnull RoleCreateEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String title = "**Обновление сервера** ";
        String action = "Создана роль";
        String text = event.getRole().getName();

        messageSender.sendGuildChangeMessage(title, action, text, event.getGuild());
    }

    @Override
    public void onRoleDelete(@Nonnull RoleDeleteEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String title = "**Обновление сервера** ";
        String action = "Роль удалена";
        String text = event.getRole().getName();

        messageSender.sendGuildChangeMessage(title, action, text, event.getGuild());
    }

    @Override
    public void onRoleUpdateName(@Nonnull RoleUpdateNameEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String title = "**Обновление сервера** ";
        String action = "Изменение названия роли";
        String text = event.getOldName() + " -> " + event.getNewName();

        messageSender.sendGuildChangeMessage(title, action, text, event.getGuild());
    }

    @Override
    public void onTextChannelCreate(@Nonnull TextChannelCreateEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String title = "**Обновление сервера** ";
        String action = "Создание текстового канала";
        String text = "**Имя канала:** " + event.getChannel().getName() + "\n"
                + "**ID:** " + event.getChannel().getId();

        messageSender.sendGuildChangeMessage(title, action, text, event.getGuild());
    }

    @Override
    public void onTextChannelDelete(@Nonnull TextChannelDeleteEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String title = "**Обновление сервера** ";
        String action = "Удаление текстового канала";
        String text = "**Имя канала:** " + event.getChannel().getName() + "\n"
                + "**ID:** " + event.getChannel().getId();

        messageSender.sendGuildChangeMessage(title, action, text, event.getGuild());
    }

    @Override
    public void onTextChannelUpdateName(@Nonnull TextChannelUpdateNameEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String title = "**Обновление сервера** ";
        String action = "Обновление имени текстового канала";
        String text = event.getOldName() + " -> " + event.getNewName();

        messageSender.sendGuildChangeMessage(title, action, text, event.getGuild());
    }

    @Override
    public void onGenericGuildUpdate(@Nonnull GenericGuildUpdateEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String title = "**Обновление сервера** ";
        String action;
        String text;
        switch (event.getPropertyIdentifier()) {
            case "name":
                action = "Обновление названия сервера";
                text = event.getOldValue() + " -> " + event.getNewValue();
                break;
            default:
                return;
        }

        messageSender.sendGuildChangeMessage(title, action, text, event.getGuild());
    }

    @Override
    public void onGuildUpdateIcon(@Nonnull GuildUpdateIconEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;

        String text = "[[До]](" + event.getOldIconUrl() + ") -> [[После]](" + event.getNewIconUrl() + ")";
        String title = "**Обновление сервера** ";
        String action = "Аватарка";

        messageSender.sendGuildChangeMessage(title, action, text, event.getGuild());
    }

    private boolean isEventDisabled(String guildId) {
        ActionService service = ActionService.getInstance();
        return !service.isActive(ActionType.LOG, guildId);
    }

}