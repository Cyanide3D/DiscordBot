package cyanide3d.listener;

import cyanide3d.repository.service.ActionService;
import cyanide3d.repository.service.ChannelService;
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
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;

public class LogListener extends ListenerAdapter {


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
    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
        String text = new StringBuilder()
                .append("Старый никнейм: ")
                .append(event.getOldNickname())
                .append("\n")
                .append("Новый никнейм: ")
                .append(event.getNewNickname())
                .toString();
        String title = "**Изменение пользователя** ";
        String action = "Никнейм";
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue();
    }

    @Override
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
        String text = event.getRoles().get(0).getName();
        String title = "**Обновление пользователя** ";
        String action = "Добавлена роль";
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue();
    }

    @Override
    public void onGuildMemberRoleRemove(@Nonnull GuildMemberRoleRemoveEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
        String text = event.getRoles().get(0).getName();
        String title = "**Обновление пользователя** ";
        String action = "Убрана роль";
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue();
    }

    @Override
    public void onGuildInviteCreate(@Nonnull GuildInviteCreateEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Cоздан инвайт";
        String text = "Автор инвайта: " + event.getInvite().getInviter().getName() + "\n"
                + "Ссылка на инвайт: " + event.getInvite().getUrl();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onRoleCreate(@Nonnull RoleCreateEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Создана роль";
        String text = event.getRole().getName();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onRoleDelete(@Nonnull RoleDeleteEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Роль удалена";
        String text = event.getRole().getName();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onRoleUpdateName(@Nonnull RoleUpdateNameEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Изменение названия роли";
        StringBuilder text = new StringBuilder();
        text.append(event.getOldName()).append(" -> ").append(event.getNewName());
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text.toString(), event.getGuild())).queue();
    }

    @Override
    public void onTextChannelCreate(@Nonnull TextChannelCreateEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Создание текстового канала";
        String text = "**Имя канала:** " + event.getChannel().getName() + "\n"
                + "**ID:** " + event.getChannel().getId();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onTextChannelDelete(@Nonnull TextChannelDeleteEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Удаление текстового канала";
        String text = "**Имя канала:** " + event.getChannel().getName() + "\n"
                + "**ID:** " + event.getChannel().getId();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onTextChannelUpdateName(@Nonnull TextChannelUpdateNameEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Обновление имени текстового канала";
        String text = event.getOldName() + " -> " + event.getNewName();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onGenericGuildUpdate(@Nonnull GenericGuildUpdateEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
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
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId())
                .sendMessage(makeMessageGuildChange(title, action, text, event.getGuild()))
                .queue();
    }

    @Override
    public void onGuildUpdateIcon(@Nonnull GuildUpdateIconEvent event) {
        if (isEventDisabled(event.getGuild().getId()))
            return;
        ChannelService channelService = ChannelService.getInstance();
        String text = new StringBuilder()
                .append("[[До]](")
                .append(event.getOldIconUrl())
                .append(") -> [[После]](")
                .append(event.getNewIconUrl())
                .append(")")
                .toString();
        String title = "**Обновление сервера** ";
        String action = "Аватарка";
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId())
                .sendMessage(makeMessageGuildChange(title, action, text, event.getGuild()))
                .queue();
    }

    private boolean isEventDisabled(String guildId) {
        ActionService service = ActionService.getInstance();
        return !service.isActive(ActionType.LOG, guildId);
    }

}