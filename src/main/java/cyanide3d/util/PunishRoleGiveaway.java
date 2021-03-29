package cyanide3d.util;

import cyanide3d.repository.service.PunishmentService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.Optional;

public class PunishRoleGiveaway {
    private static final PunishmentService service = PunishmentService.getInstance();

    public static void giveRoleToUser(Guild guild, Member member) {
        Optional.ofNullable(guild.getRoleById(service.getMutedRoleId(guild.getId())))
                .ifPresent(e -> guild.addRoleToMember(member, e).queue());
    }

}
