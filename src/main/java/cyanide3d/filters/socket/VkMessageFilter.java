package cyanide3d.filters.socket;

import net.dv8tion.jda.api.entities.Guild;

public interface VkMessageFilter {
    String filter(String message, Guild guild);
}
