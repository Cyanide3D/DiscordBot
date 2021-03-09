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
    private Map<String, Role> roles;
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
                return "Введите текст.";
            case TEXT:
                builder.setDescription(message.getContentRaw());
                state = ParserState.CHANNEL_ID;
                return "Введите ИД канала с сообщением.";
            case CHANNEL_ID:
                channelID = message.getContentRaw();
                state = ParserState.ROLES_COUNT;
                return "Сколько авторолей будет в сообщении? (Цифра)";
            case ROLES_COUNT:
                if (NumberUtils.isParsable(message.getContentRaw())) {
                    clazz = MessageReactionAddEvent.class;
                    rolesCount = NumberUtils.toInt(message.getContentRaw());
                    roles = new HashMap<>(rolesCount);
                    state = ParserState.EMOJI;
                    return "Поставьте эмодзи под сообщением.";
                }
                return "слишком строчно, попробоуйте числее";
            case EMOJI:
                clazz = GuildMessageReceivedEvent.class;
                //emoji = message.getContentRaw();
                emoji = emote.getName();
                state = ParserState.ROLE;
                return "Укажите роль.";
            case ROLE:
                roles.put(emoji, message.getMentionedRoles().get(0));
                if (roles.size() == rolesCount) {
                    state = ParserState.DONE;
                    //roles.forEach((k,v) -> System.out.println(k + " " + v.getName()));
                    return "завершено";
                } else {
                    state = ParserState.EMOJI;
                    clazz = MessageReactionAddEvent.class;
                    return "Поставьте эмодзи под сообщением.";
                }
            default:
                return "невозможно!";
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
