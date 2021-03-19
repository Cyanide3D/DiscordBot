package cyanide3d.filters.socket;

import cyanide3d.filters.socket.discord.RoleMentionFilter;
import cyanide3d.filters.socket.vk.AllMentionFilter;
import cyanide3d.filters.socket.vk.UserMentionFilter;
import net.dv8tion.jda.api.entities.Guild;

import java.util.List;

public class SocketFilter {
    private String message;

    public SocketFilter(String message) {
        this.message = message;
    }

    public String toDiscord(Guild guild) {
        List<VkMessageFilter> filters = List.of(
                new cyanide3d.filters.socket.vk.RoleMentionFilter(),
                new AllMentionFilter(),
                new UserMentionFilter()
        );
        filters.forEach(filter ->
                message = filter.filter(message, guild));

        return message;
    }

    public String toVk(Guild guild) {
        List<DiscordMessageFilter> filters = List.of(
                new RoleMentionFilter()
        );
        filters.forEach(filter ->
                message = filter.filter(message, guild));

        return message;
    }
}
