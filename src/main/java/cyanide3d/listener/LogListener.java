package cyanide3d.listener;

import cyanide3d.dto.ChannelEntity;
import cyanide3d.service.ChannelService;
import cyanide3d.service.MessageService;
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
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
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
    public void onUserUpdateAvatar(@Nonnull UserUpdateAvatarEvent event) {
        String text = new StringBuilder()
                .append("[[До]](")
                .append(event.getOldAvatarUrl())
                .append(") -> [[После]](")
                .append(event.getNewAvatarUrl())
                .append(")")
                .toString();
        String title = "**Изменение пользователя** ";
        String action = "Аватарка";
        event.getUser().getMutualGuilds().stream().forEach(guild -> {
            ChannelService channelService = ChannelService.getInstance();
            channelService.getEventChannel(event.getJDA(), ActionType.LOG, guild.getId()).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue();
        });

    }

    @Override
    public void onGuildMessageUpdate(@Nonnull GuildMessageUpdateEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        MessageService service = MessageService.getInstance();
        String text = service.getMessageById(event.getMessageId(), event.getGuild().getId()).getBody() + " -> " + event.getMessage().getContentRaw();
        String title = "**Пользователь** ";
        String action = "Изменение сообщения";
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId())
                .sendMessage(
                        makeMessageUserChange(title, action, text.length() >= 1000
                                ? "Слишком длинное сообщение."
                                : text, event.getAuthor())
                ).queue();
        service.delete(event.getMessageId());
        service.add(event.getMessageId(), event.getMessage().getContentRaw(), event.getGuild().getId());
    }

    @Override
    public void onUserUpdateName(@Nonnull UserUpdateNameEvent event) {
        String text = new StringBuilder()
                .append("Старое имя профиля: ")
                .append(event.getOldName())
                .append("\n")
                .append("Новое имя профиля: ")
                .append(event.getNewName())
                .toString();
        String title = "**Изменение пользователя** ";
        String action = "Имя профиля";

        event.getUser().getMutualGuilds().stream().forEach(guild ->{
            ChannelService channelService = ChannelService.getInstance();
            channelService.getEventChannel(event.getJDA(), ActionType.LOG, guild.getId()).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue();
        });

    }

    @Override
    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event) {
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
        ChannelService channelService = ChannelService.getInstance();
        String text = event.getRoles().get(0).getName();
        String title = "**Обновление пользователя** ";
        String action = "Добавлена роль";
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue();
    }

    @Override
    public void onGuildMemberRoleRemove(@Nonnull GuildMemberRoleRemoveEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        String text = event.getRoles().get(0).getName();
        String title = "**Обновление пользователя** ";
        String action = "Убрана роль";
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageUserChange(title, action, text, event.getUser())).queue();
    }

    @Override
    public void onGuildInviteCreate(@Nonnull GuildInviteCreateEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Cоздан инвайт";
        String text = "Автор инвайта: " + event.getInvite().getInviter().getName() + "\n"
                + "Ссылка на инвайт: " + event.getInvite().getUrl();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onRoleCreate(@Nonnull RoleCreateEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Создана роль";
        String text = event.getRole().getName();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onRoleDelete(@Nonnull RoleDeleteEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Роль удалена";
        String text = event.getRole().getName();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onRoleUpdateName(@Nonnull RoleUpdateNameEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Изменение названия роли";
        StringBuilder text = new StringBuilder();
        text.append(event.getOldName()).append(" -> ").append(event.getNewName());
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text.toString(), event.getGuild())).queue();
    }

    @Override
    public void onTextChannelCreate(@Nonnull TextChannelCreateEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Создание текстового канала";
        String text = "**Имя канала:** " + event.getChannel().getName() + "\n"
                + "**ID:** " + event.getChannel().getId();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onTextChannelDelete(@Nonnull TextChannelDeleteEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Удаление текстового канала";
        String text = "**Имя канала:** " + event.getChannel().getName() + "\n"
                + "**ID:** " + event.getChannel().getId();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

    @Override
    public void onTextChannelUpdateName(@Nonnull TextChannelUpdateNameEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Обновление имени текстового канала";
        String text = event.getOldName() + " -> " + event.getNewName();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
    }

//    @Override
//    public void onGuildVoiceMute(@Nonnull GuildVoiceMuteEvent event) {
//        if (!enableActionService.getState("logging")) {
//            return;
//        }
//        String title = "**Голосовой канал** ";
//        String action = "Мут";
//        String text = event.isMuted() ? event.getMember().getUser().getName() + " замьючен." : event.getMember().getUser().getName() + " размьючен.";
//        channelManagmentService.loggingChannel(event.getGuild()).sendMessage(makeMessageGuildChange(title, action, text, event.getGuild())).queue();
//    }

    @Override
    public void onGenericGuildUpdate(@Nonnull GenericGuildUpdateEvent event) {
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

    @Override
    public void onGuildMessageDelete(@Nonnull GuildMessageDeleteEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        MessageService service = MessageService.getInstance();
        String title = "**Обновление сервера** ";
        String action = "Удаление сообщения";
        String text = event.getChannel().getAsMention() + " -> " + service.getMessageById(event.getMessageId(), event.getGuild().getId()).getBody();
        channelService.getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId())
                .sendMessage(makeMessageGuildChange(title, action, text, event.getGuild()))
                .queue();
        service.delete(event.getMessageId());

    }
}