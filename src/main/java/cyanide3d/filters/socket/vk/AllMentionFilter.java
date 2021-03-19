package cyanide3d.filters.socket.vk;

import cyanide3d.filters.socket.VkMessageFilter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class AllMentionFilter implements VkMessageFilter {
    @Override
    public String filter(String message, Guild guild) {
        List<Role> roles = guild.getRolesByName("Лентяи", true);
        if (!roles.isEmpty()) {
            Role role = roles.get(0);
            message = message.replace("@all", role.getAsMention());
        }
        return message;
    }
}
