package cyanide3d.filters.socket.discord;

import cyanide3d.filters.socket.MessageFilter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoleMentionFilter implements MessageFilter {
    @Override
    public String execute(String message, Guild guild) {
        Pattern pattern = Pattern.compile("<@.*?>");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String mention = matcher.group();
            String id = getIdFromMention(mention);
            message = replaceMemberMention(id, mention, guild, message);
            message = replaceRoleMention(id, mention, guild, message);
        }
        return message;
    }
    private String replaceRoleMention(String id, String mention, Guild guild, String message) {
        Role role = guild.getRoleById(id);
        if (role != null) {
            String replace = role.getId().equals("664863242199236629") ? "@all" : "@" + role.getName().toUpperCase();
            message = message.replace(mention, replace);
        }
        return message;
    }

    private String replaceMemberMention(String id, String mention, Guild guild, String message) {
        Member member = guild.getMemberById(id);
        if (member != null) {
            String name = (String) ObjectUtils.defaultIfNull(member.getNickname(), member.getUser());
            message = message.replace(mention, name);
        }
        return message;
    }

    private String getIdFromMention(String mention) {
        String id = StringUtils.substringAfter(mention, "!");
        if (id.isEmpty()) {
            id = StringUtils.substringAfter(mention, "&");
            if (id.isEmpty()) {
                id = StringUtils.substringAfter(mention, "@");
            }
        }
        return id.replace(">", "");
    }
}
