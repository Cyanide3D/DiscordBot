package cyanide3d.commands.moderation.emoji;

import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.repository.service.AutoroleService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StatefulParser {
    private final EmbedBuilder builder;
    private ParserState state;
    private Map<String, String> roles;
    private int rolesCount;
    private String channelID;
    private String emoji;

    public StatefulParser() {
        builder = new EmbedBuilder()
                .setColor(Color.ORANGE);
        state = ParserState.TITLE;
    }

    public String parse(GenericGuildMessageEvent event) {
        String emoteName = null, contentRaw = null, roleId = null;

        if (event instanceof GuildMessageReceivedEvent) {
            Message message = ((GuildMessageReceivedEvent) event).getMessage();
            contentRaw = message.getContentRaw();
            if (message.getMentionedRoles().size() > 0) {
                roleId = message.getMentionedRoles().get(0).getId().intern();
            }
        } else if (event instanceof GuildMessageReactionAddEvent) {
            emoteName = ((GuildMessageReactionAddEvent) event).getReactionEmote().getName().intern();
        }

        switch (state) {
            case TITLE:
                builder.setTitle(contentRaw);
                state = ParserState.TEXT;
                return "Введите текст сообщения.";
            case TEXT:
                builder.setDescription(contentRaw);
                state = ParserState.CHANNEL_ID;
                return "Введите ID канала.";
            case CHANNEL_ID:
                channelID = contentRaw;
                state = ParserState.ROLES_COUNT;
                return "Сколько авторолей будет в сообщении? (В цифрах)";
            case ROLES_COUNT:
                if (NumberUtils.isParsable(contentRaw)) {
                    rolesCount = NumberUtils.toInt(contentRaw);
                    roles = new HashMap<>(rolesCount);
                    state = ParserState.EMOJI;
                    return "Поставьте эмоцию под сообщением.";
                }
                return "Слишком строчно, попробоуйте числее";
            case EMOJI:
                emoji = emoteName;
                state = ParserState.ROLE;
                return "Линканите роль.";
            case ROLE:
                roles.put(emoji, roleId);
                emoji = null;//на всякий ебучий случай (ну и заодно чтоб гц почистил)
                if (roles.size() == rolesCount) {
                    state = ParserState.DONE;
                    return "Готово!";
                } else {
                    state = ParserState.EMOJI;
                    return "Поставьте эмоцию под сообщением.";
                }
            default:
                return "Что то пошло не так!";
        }
    }

    /*FIXME
       Не до конца понял, что именно делает этот метод, но он точно не инит.
       Выглядит, что он применяет изменения и правит сообщение в соответствии с ними.
    */
    public void apply(CommandEvent event) {
        final TextChannel channel = event.getGuild().getTextChannelById(channelID);
        if (channel == null) {
            event.reply("Канала с таким ID не существует.");
            return;
        }

        final Message message = channel.sendMessage(builder.build()).complete();
        final Set<String> strings = roles.keySet();
        for (String string : strings) {
            message.addReaction(string).queue();
        }

        AutoroleService.getInstance().addAutorole(message.getId(), roles, event.getGuild().getId());
    }

    public boolean isComplete() {
        return state == ParserState.DONE;
    }

    public Class<? extends GenericGuildMessageEvent> getNextEventClass() {
        switch (state) {
            case INIT:
            case TITLE:
            case TEXT:
            case CHANNEL_ID:
            case ROLES_COUNT:
            case ROLE://Всё взаимодействие идёт через сообщения
                return GuildMessageReceivedEvent.class;
            case EMOJI://И только в этом месте нужна эможзя
                return GuildMessageReactionAddEvent.class;
            default:
                //сюда попадёт DONE и все новые состояния, которые ты забудешь добавить наверх.
                //В этом случае никакого эвента уже не ждём
                return null;
        }
    }

    private enum ParserState {
        INIT, TITLE, TEXT, CHANNEL_ID, ROLES_COUNT, EMOJI, ROLE, DONE
    }
}
