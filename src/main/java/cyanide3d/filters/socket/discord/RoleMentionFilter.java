package cyanide3d.filters.socket.discord;

import cyanide3d.filters.socket.DiscordMessageFilter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoleMentionFilter implements DiscordMessageFilter {
    @Override
    public String filter(String message, Guild guild) {
        Pattern pattern = Pattern.compile("<@.*?>");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String mention = matcher.group();
            String id = getIdFromMention(mention);
            replaceMemberMention(id, mention, guild, message);
            replaceRoleMention(id, mention, guild, message);
        }
        return message;
    }
    private void replaceRoleMention(String id, String mention, Guild guild, String message) {
        Role role = guild.getRoleById(id);
        if (role != null) {
            message = message.replace(mention, "@" + role.getName().toUpperCase());
        }
    }

    private void replaceMemberMention(String id, String mention, Guild guild, String message) {
        Member member = guild.getMemberById(id);
        if (member != null) {
            String name = member.getNickname() == null ? member.getUser().getName() : member.getNickname();
            message = message.replace(mention, name);
        }
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
