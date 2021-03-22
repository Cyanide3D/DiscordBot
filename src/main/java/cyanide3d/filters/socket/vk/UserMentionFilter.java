package cyanide3d.filters.socket.vk;

import cyanide3d.filters.socket.MessageFilter;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMentionFilter implements MessageFilter {
    @Override
    public String execute(String message, Guild guild) {
        Pattern pattern = Pattern.compile("\\[id.*?\\|.*?\\]");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String mention = matcher.group();
            message = message.replace(mention, "**" + StringUtils.substringAfter(mention, "|").replace("]", "") + "**");
        }
        return message;
    }
}
