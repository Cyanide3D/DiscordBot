package cyanide3d.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.exceptions.PunishmentDuplicateException;
import cyanide3d.exceptions.PunishmentNotFoundException;
import cyanide3d.repository.model.PunishmentUserEntity;
import cyanide3d.repository.service.PunishmentService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;

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

    public Map<String, Set<PunishmentUserEntity>> getPunishedUserList() {
        return service.getUsersToUnmute();
    }

    public void disable(CommandEvent event) {
        try {
            service.disable(event.getGuild().getId());
            event.reply("Наказания успешно отключены!");
        } catch (PunishmentNotFoundException e) {
            event.reply("Наказания на сервере были отключены!");
        }
    }

    public void punish(GuildMessageReceivedEvent event) {
        try {
            if (service.increaseViolation(event.getGuild().getId(), event.getMember().getId())) {
                roleGiveaway.giveRoleToUser(event.getGuild(), event.getMember());
            }
        } catch (IllegalArgumentException ignored) {
        }
    }

    public void startPunishmentCheck(JDA jda) {
        Timer timer = new Timer();
        timer.schedule(new UserUnmuteVerifier(jda), 0, 10000);
    }

    public void deleteUser(PunishmentUserEntity e, String guildId) {
        service.deleteUserFromEntity(e, guildId);
    }

    public void deleteEntity(String guildId) {
        service.disable(guildId);
    }
}
