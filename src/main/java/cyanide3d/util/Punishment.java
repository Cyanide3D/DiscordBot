package cyanide3d.util;

import cyanide3d.exceptions.PunishmentNotFoundException;
import cyanide3d.repository.model.PunishmentUserEntity;
import cyanide3d.repository.service.PunishmentService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;

public class Punishment {

    private final PunishmentService service;
    private final PunishRoleGiveaway roleGiveaway;
    private final Logger logger;

    public Punishment() {
        roleGiveaway = new PunishRoleGiveaway();
        service = PunishmentService.getInstance();
        logger = LoggerFactory.getLogger(Punishment.class);
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

    public void startPunishmentCheck(JDA jda) {
        Timer timer = new Timer();
        timer.schedule(new UnmuteTrigger(jda, this), 0, 10000);
        logger.info("Punish verifier started...");
    }
}
