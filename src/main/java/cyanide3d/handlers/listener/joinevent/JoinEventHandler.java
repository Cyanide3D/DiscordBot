package cyanide3d.handlers.listener.joinevent;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public interface JoinEventHandler {
    void execute(GuildMemberJoinEvent event);
}
