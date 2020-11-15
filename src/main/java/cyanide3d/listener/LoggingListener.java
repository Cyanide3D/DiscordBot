package cyanide3d.listener;

import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.EnableActionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateIconEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.GenericRoleUpdateEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;

public class LoggingListener extends ListenerAdapter {

    EnableActionService enableActionService = EnableActionService.getInstance();
    ChannelManagmentService channelManagmentService = ChannelManagmentService.getInstance();

    public MessageEmbed makeMessageUserChange(String title, String event, String text, User user) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setAuthor(user.getAsTag(), null, user.getAvatarUrl())
                .setDescription(title + user.getAsMention())
                .addField(event, text, false)
                .setThumbnail(user.getEffectiveAvatarUrl())
                .setFooter("ID Пользователя: " + user.getId())
                .build();
    }

    public MessageEmbed makeMessageGuildChange(String title, String event, String text, Guild guild) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setDescription(title)
                .addField(event, text, false)
                .setThumbnail(guild.getIconUrl())
                .setFooter("ID Сервера: " + guild.getId())
                .build();
    }

    @Override
    public void onUserUpdateAvatar(@Nonnull UserUpdateAvatarEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String text = new StringBuilder()
                .append("[[До]](")
                .append(event.getOldAvatarUrl())
                .append(") -> [[После]](")
                .append(event.getNewAvatarUrl())
                .append(")")
                .toString();
        String title = "**Изменение пользователя** ";
        String action = "Аватарка";
        event.getUser().getMutualGuilds().stream().forEach(guild ->
                channelManagmentService.loggingChannel(guild).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue());
    }

    @Override
    public void onGuildMessageUpdate(@Nonnull GuildMessageUpdateEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String text = event.getChannel().getAsMention() + " -> " + event.getMessage().getContentRaw();
        String title = "**Пользователь** ";
        String action = "Изменение сообщения";
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageUserChange(title, action, text, event.getAuthor())).queue();
    }

    @Override
    public void onUserUpdateName(@Nonnull UserUpdateNameEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String text = new StringBuilder()
                .append("Старое имя профиля: ")
                .append(event.getOldName())
                .append("\n")
                .append("Новое имя профиля: ")
                .append(event.getNewName())
                .toString();
        String title = "**Изменение пользователя** ";
        String action = "Имя профиля";
        event.getUser().getMutualGuilds().stream().forEach(guild ->
                channelManagmentService.loggingChannel(guild).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue());
    }

    @Override
    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String text = new StringBuilder()
                .append("Старый никнейм: ")
                .append(event.getOldNickname())
                .append("\n")
                .append("Новый никнейм: ")
                .append(event.getNewNickname())
                .toString();
        String title = "**Изменение пользователя** ";
        String action = "Никнейм";
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue();
    }

    @Override
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String text = event.getRoles().get(0).getName();
        String title = "**Обновление пользователя** ";
        String action = "Добавлена роль";
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue();
    }

    @Override
    public void onGuildMemberRoleRemove(@Nonnull GuildMemberRoleRemoveEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String text = event.getRoles().get(0).getName();
        String title = "**Обновление пользователя** ";
        String action = "Убрана роль";
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue();
    }

    @Override
    public void onGuildInviteCreate(@Nonnull GuildInviteCreateEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String title = "**Обновление сервера** ";
        String action = "Cоздан инвайт";
        String text = "Автор инвайта: " + event.getInvite().getInviter().getName() + "\n"
                + "Ссылка на инвайт: " + event.getInvite().getUrl();
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onRoleCreate(@Nonnull RoleCreateEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String title = "**Обновление сервера** ";
        String action = "Создана роль";
        String text = event.getRole().getName();
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onRoleDelete(@Nonnull RoleDeleteEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String title = "**Обновление сервера** ";
        String action = "Роль удалена";
        String text = event.getRole().getName();
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onRoleUpdateName(@Nonnull RoleUpdateNameEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String title = "**Обновление сервера** ";
        String action = "Изменение названия роли";
        StringBuilder text = new StringBuilder();
        text.append(event.getOldName()).append(" -> ").append(event.getNewName());
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageGuildChange(title, action, text.toString(), event.getGuild())).queue();
    }

    @Override
    public void onTextChannelCreate(@Nonnull TextChannelCreateEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String title = "**Обновление сервера** ";
        String action = "Создание текстового канала";
        String text = "**Имя канала:** " + event.getChannel().getName() + "\n"
                + "**ID:** " + event.getChannel().getId();
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onTextChannelDelete(@Nonnull TextChannelDeleteEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String title = "**Обновление сервера** ";
        String action = "Удаление текстового канала";
        String text = "**Имя канала:** " + event.getChannel().getName() + "\n"
                + "**ID:** " + event.getChannel().getId();
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onTextChannelUpdateName(@Nonnull TextChannelUpdateNameEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String title = "**Обновление сервера** ";
        String action = "Обновление имени текстового канала";
        String text = event.getOldName() + " -> " + event.getNewName();
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onGuildVoiceMute(@Nonnull GuildVoiceMuteEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String title = "**Голосовой канал** ";
        String action = "Мут";
        String text = event.isMuted() ? event.getMember().getUser().getName() + " замьючен." : event.getMember().getUser().getName() + " размьючен.";
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onGenericGuildUpdate(@Nonnull GenericGuildUpdateEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String title = "**Обновление сервера** ";
        String action;
        String text;
        switch (event.getPropertyIdentifier()) {
            case "name":
                action = "Обновление названия сервера";
                text = (String) event.getOldValue() + " -> " + (String) event.getNewValue();
                break;
            default:
                return;
        }
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onGuildUpdateIcon(@Nonnull GuildUpdateIconEvent event) {
        if (!enableActionService.getState("logging")) {
            return;
        }
        String text = new StringBuilder()
                .append("[[До]](")
                .append(event.getOldIconUrl())
                .append(") -> [[После]](")
                .append(event.getNewIconUrl())
                .append(")")
                .toString();
        String title = "**Обновление сервера** ";
        String action = "Аватарка";
        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }
}