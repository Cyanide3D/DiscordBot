package cyanide3d.filters.socket;

import net.dv8tion.jda.api.entities.Guild;

public interface DiscordMessageFilter {
    String filter(String message, Guild guild);
}
