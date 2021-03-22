package cyanide3d.filters.socket;

import net.dv8tion.jda.api.entities.Guild;

public interface MessageFilter {
    String execute(String message, Guild guild);
}
