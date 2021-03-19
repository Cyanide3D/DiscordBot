package cyanide3d.filters.socket.vk;

import cyanide3d.filters.socket.VkMessageFilter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoleMentionFilter implements VkMessageFilter {
    @Override
    public String filter(String message, Guild guild) {
        Pattern pattern = Pattern.compile("@\\w*\\b");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String role = matcher.group();
            final List<Role> rolesByName = guild.getRolesByName(StringUtils.substringAfter(role, "@"), true);
            if (!rolesByName.isEmpty() && !role.equalsIgnoreCase("@all")) {
                message = message.replace(role, rolesByName.get(0).getAsMention());
            }
        }
        return message;
    }
}
