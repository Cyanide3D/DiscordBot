package cyanide3d.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.exceptions.PunishmentDuplicateException;
import cyanide3d.exceptions.PunishmentNotFoundException;
import cyanide3d.repository.model.PunishmentUserEntity;
import cyanide3d.repository.service.PunishmentService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;

public class Punishment {

    private final PunishmentService service = PunishmentService.getInstance();
    private final PunishRoleGiveaway roleGiveaway = new PunishRoleGiveaway();

    public void enable(CommandEvent event, String roleId, String[] args) {
        try {
            service.enable(event.getGuild().getId(), Integer.parseInt(args[1]), roleId, Integer.parseInt(args[2]));
            event.reply("Наказания успешно включены!");
        } catch (PunishmentDuplicateException e) {
            event.reply("Наказания уже включены для этого сервера!");
        }
    }

    public void disable(CommandEvent event) {
        try {
            service.disable(event.getGuild().getId());
            event.reply("Наказания успешно отключены!");
        } catch (PunishmentNotFoundException e) {
            event.reply("Наказания на сервере были отключены!");
        }
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
            for (PunishmentUserEntity user : users) {
                Optional.ofNullable(guild.getMemberById(user.getUserId())).ifPresent(member -> {
                    roleGiveaway.removeRoleFromUser(guild, member);
                });
                service.deleteUserFromEntity(user, guildId);
            }
        }, () -> service.disable(guildId));
    }

    public void startPunishmentCheck(JDA jda) {
        Timer timer = new Timer();
        timer.schedule(new UnmuteTrigger(jda), 0, 10000);
    }
}
