package cyanide3d.handlers.listener.joinevent;

import cyanide3d.repository.service.BlacklistService;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public class KickBlacklistedHandler implements JoinEventHandler{

    BlacklistService service;

    public KickBlacklistedHandler() {
        service = BlacklistService.getInstance();
    }

    @Override
    public void execute(GuildMemberJoinEvent event) {
        if (isBlacklisted(event)) {
            event.getGuild().kick(event.getMember(), "ЧС").queue();
        }
    }

    private boolean isBlacklisted(GuildMemberJoinEvent event) {
        return service.getBlacklistedUserIDs(event.getGuild().getId()).stream()
                .anyMatch(id -> event.getMember().getId().equals(id));
    }

}
