package cyanide3d.commands.mod.emoji;

import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.service.EmoteManageService;
import javafx.scene.effect.Effect;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
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
                .setColor(Color.ORANGE)
                .setFooter("From Defiant'S with love ;)");
        state = ParserState.TITLE;
    }

    public String parse(GenericGuildMessageEvent event) {
        Message message = null;
        ReactionEmote emote = null;

        if (event instanceof GuildMessageReceivedEvent) {
            message = ((GuildMessageReceivedEvent) event).getMessage();
        } else if (event instanceof GuildMessageReactionAddEvent) {
            emote = ((GuildMessageReactionAddEvent) event).getReactionEmote();
        }

        //TODO проверку сообщений и эмодзь на null, хотя по хорошему неверный эвент прийти не может

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
                    rolesCount = NumberUtils.toInt(message.getContentRaw());
                    roles = new HashMap<>(rolesCount);
                    state = ParserState.EMOJI;
                    return "Поставьте эмоцию под сообщением.";
                }
                return "Слишком строчно, попробоуйте числее";
            case EMOJI:
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

        EmoteManageService.getInstance().save(message.getId(), roles);
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
