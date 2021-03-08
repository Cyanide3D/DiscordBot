package cyanide3d.commands.mod.replyer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StatefulParser {
    private final EmbedBuilder builder;
    private ParserState state;
    private Map<String, Role> roles;
    private int rolesCount;
    private String emoji;

    public StatefulParser() {
        builder = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setFooter("From De  fiant'S with love ;)");
        state = ParserState.TITLE;
    }

    public String parse(Message message) {
        final String messageText = message.getContentRaw();
        switch (state) {
            case TITLE:
                builder.setTitle(messageText);
                state = ParserState.TEXT;
                return "Введите текст.";
            case TEXT:
                builder.setDescription(messageText);
                state = ParserState.ROLES_COUNT;
                return "Сколько авторолей будет в сообщении? (Цифра)";
            case ROLES_COUNT:
                if (NumberUtils.isParsable(messageText)) {
                    rolesCount = NumberUtils.toInt(messageText);
                    roles = new HashMap<>(rolesCount);
                    state = ParserState.EMOJI;
                    return "Введите ИД эмодзи.";
                }
                return "слишком строчно, попробоуйте числее";
            case EMOJI:
                emoji = messageText;
                state = ParserState.EMOJI;
                return "Укажите роль.";
            case ROLE:
                roles.put(emoji, message.getMentionedRoles().get(0));
                if (roles.size() == rolesCount) {
                    state = ParserState.DONE;
                    return "завершено";
                } else {
                    state = ParserState.EMOJI;
                    return "Введите ИД эмодзи.";
                }
            default:
                return "невозможно!";
        }
    }

    public boolean isComplete() {
        return state == ParserState.DONE;
    }

    private enum ParserState {
        INIT, TITLE, TEXT, ROLES_COUNT, EMOJI, ROLE, DONE
    }
}
