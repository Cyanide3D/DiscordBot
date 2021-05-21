package cyanide3d.filters.socket.vk;

import cyanide3d.filters.socket.MessageFilter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class AllMentionFilter implements MessageFilter {
    @Override
    public String execute(String message, Guild guild) {
        Role role = guild.getRoleById("664863242199236629");
        if (role != null) {
            message = message.replace("@all", role.getAsMention());
        }
        return message;
    }
}
