package cyanide3d.util;

import cyanide3d.repository.model.PunishmentEntity;
import cyanide3d.repository.model.PunishmentUserEntity;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimerTask;

public class UserUnmuteVerifier extends TimerTask {

    private final JDA jda;
    private final Punishment punishment;
    private final PunishRoleGiveaway roleGiveaway;

    public UserUnmuteVerifier(JDA jda) {
        roleGiveaway = new PunishRoleGiveaway();
        punishment = new Punishment();
        this.jda = jda;
    }

    @Override
    public void run() {
        Map<String, Set<PunishmentUserEntity>> punishedUserList = punishment.getPunishedUserList();
        punishedUserList.forEach(this::unmuteUser);
    }

    private void unmuteUser(String guildId, Set<PunishmentUserEntity> users) {
        try {
            Guild guild = jda.getGuildById(guildId);

            for (PunishmentUserEntity user : users) {
                Optional.ofNullable(guild.getMemberById(user.getUserId())).ifPresent(member -> {
                    roleGiveaway.removeRoleFromUser(guild, member);
                });
                punishment.deleteUser(user, guildId);
            }

        } catch (Exception ignore) {
            punishment.deleteEntity(guildId);
        }
    }
}
