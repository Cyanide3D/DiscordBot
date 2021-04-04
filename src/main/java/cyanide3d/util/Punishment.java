package cyanide3d.util;

import cyanide3d.exceptions.PunishmentNotFoundException;
import cyanide3d.repository.model.PunishmentUserEntity;
import cyanide3d.repository.service.PunishmentService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Punishment extends SimpleTimerTask {

    private final PunishmentService service = PunishmentService.getInstance();
    private final PunishRoleGiveaway roleGiveaway = new PunishRoleGiveaway();
    private JDA jda;

    public Punishment() {
    }

    public Punishment(JDA jda) {
        this.jda = jda;
    }

    public void enable(String guildId, int violationsBeforeMute, String roleId, int punishmentTime) {
        service.enable(guildId, violationsBeforeMute, roleId, punishmentTime);
    }

    public void disable(String guildId) {
        service.disable(guildId);
    }

    public void punish(Guild guild, Member member) {
        try {
            if (service.increaseViolation(guild.getId(), member.getId())) {
                roleGiveaway.giveRoleToUser(guild, member);
            }
        } catch (PunishmentNotFoundException ignore) {
        }
    }

    public void release(JDA jda) {
        Map<String, Set<PunishmentUserEntity>> punishedUserList = service.getUsersToUnmute();
        punishedUserList.forEach((k, v) -> deleteMutedUsers(jda, k, v));
    }

    private void deleteMutedUsers(JDA jda, String guildId, Set<PunishmentUserEntity> users) {
        Optional.ofNullable(jda.getGuildById(guildId)).ifPresentOrElse(guild -> {
            deleteUser(users, guild);
        }, () -> service.disable(guildId));
    }

    private void deleteUser(Set<PunishmentUserEntity> users, Guild guild) {
        for (PunishmentUserEntity user : users) {
            Optional.ofNullable(guild.getMemberById(user.getUserId())).ifPresent(member -> {
                roleGiveaway.removeRoleFromUser(guild, member);
            });
            service.deletePunishedUser(user);
        }
    }

    @Override
    public void run() {
        release(jda);
    }

    @Override
    public int getPeriod() {
        return 10000;
    }

    @Override
    public int getDelay() {
        return 0;
    }
}
