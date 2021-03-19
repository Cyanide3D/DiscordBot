package cyanide3d.util;

import cyanide3d.filters.socket.DiscordMessageFilter;
import cyanide3d.filters.socket.VkMessageFilter;
import cyanide3d.filters.socket.discord.RoleMentionFilter;
import cyanide3d.filters.socket.vk.AllMentionFilter;
import cyanide3d.filters.socket.vk.UserMentionFilter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
