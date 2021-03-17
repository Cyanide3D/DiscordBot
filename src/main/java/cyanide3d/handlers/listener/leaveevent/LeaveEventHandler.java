package cyanide3d.handlers.listener.leaveevent;

import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

public interface LeaveEventHandler {
    void execute(GuildMemberRemoveEvent event);
}
