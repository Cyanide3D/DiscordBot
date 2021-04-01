package cyanide3d.util;

import cyanide3d.repository.service.PunishmentService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Optional;

public class Punishment {
    private static final PunishmentService service = PunishmentService.getInstance();

    public static void punish(GuildMessageReceivedEvent event) {
        try {
            if (service.increaseViolation(event.getGuild().getId(), event.getMember().getId())) {
                giveRoleToUser(event.getGuild(), event.getMember());
            }
        } catch (IllegalArgumentException ignored) {
        }
    }

    private static void giveRoleToUser(Guild guild, Member member) {
        Optional.ofNullable(guild.getRoleById(service.getMutedRoleId(guild.getId())))
                .ifPresent(e -> guild.addRoleToMember(member, e).queue());
    }

}
