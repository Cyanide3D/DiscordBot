package cyanide3d.commands.mod.emoji;

import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.service.EmoteManageService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StatefulParser {
    private final EmbedBuilder builder;
    private ParserState state;
    private Class clazz = GuildMessageReceivedEvent.class;
    private Map<String, String> roles;
    private int rolesCount;
    private String channelID;
    private String emoji;

    public StatefulParser() {
        builder = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setFooter("From Defiant'S with love ;)");
        state = ParserState.TITLE;
    }

    private String handle(Message message, MessageReaction.ReactionEmote emote) {
        switch (state) {
            case TITLE:
                builder.setTitle(message.getContentRaw());
                state = ParserState.TEXT;
                return "Введите текст сообщения.";
            case TEXT:
                builder.setDescription(message.getContentRaw());
                state = ParserState.CHANNEL_ID;
                return "Введите ID канала.";
            case CHANNEL_ID:
                channelID = message.getContentRaw();
                state = ParserState.ROLES_COUNT;
                return "Сколько авторолей будет в сообщении? (В цифрах)";
            case ROLES_COUNT:
                if (NumberUtils.isParsable(message.getContentRaw())) {
                    clazz = MessageReactionAddEvent.class;
                    rolesCount = NumberUtils.toInt(message.getContentRaw());
                    roles = new HashMap<>(rolesCount);
                    state = ParserState.EMOJI;
                    return "Поставьте эмоцию под сообщением.";
                }
                return "Слишком строчно, попробоуйте числее";
            case EMOJI:
                clazz = GuildMessageReceivedEvent.class;
                emoji = emote.getName();
                state = ParserState.ROLE;
                return "Линканите роль.";
            case ROLE:
                roles.put(emoji, message.getMentionedRoles().get(0).getId());
                if (roles.size() == rolesCount) {
                    state = ParserState.DONE;
                    return "Готово!";
                } else {
                    state = ParserState.EMOJI;
                    clazz = MessageReactionAddEvent.class;
                    return "Поставьте эмоцию под сообщением.";
                }
            default:
                return "Что то пошло не так!";
        }
    }

    public String parse(Message message) {
        return handle(message, null);
    }

    public String parse(MessageReaction.ReactionEmote emote) {
        return handle(null, emote);
    }

    public void init(CommandEvent event) {
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

        EmoteManageService.getInstance().save(message.getId(), roles);
    }

    public <T> Class<T> getEventClass() {
        return clazz;
    }

    public boolean isComplete() {
        return state == ParserState.DONE;
    }

    private enum ParserState {
        INIT, TITLE, TEXT, CHANNEL_ID, ROLES_COUNT, EMOJI, ROLE, DONE
    }
}
