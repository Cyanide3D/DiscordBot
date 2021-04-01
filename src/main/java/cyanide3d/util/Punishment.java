package cyanide3d.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.exceptions.PunishmentDuplicateException;
import cyanide3d.exceptions.PunishmentNotFoundException;
import cyanide3d.repository.service.PunishmentService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Optional;

public class Punishment {
    private static final PunishmentService service = PunishmentService.getInstance();

    public static void enable(CommandEvent event, String roleId, String[] args) {
        try {
            service.enable(event.getGuild().getId(), Integer.parseInt(args[1]), roleId, Integer.parseInt(args[2]));
            event.reply("Наказания успешно включены!");
        } catch (PunishmentDuplicateException e) {
            event.reply("Наказания уже включены для этого сервера!");
        }
    }

    public static void disable(CommandEvent event) {
        try {
            service.disable(event.getGuild().getId());
            event.reply("Наказания успешно отключены!");
        } catch (PunishmentNotFoundException e) {
            event.reply("Наказания на сервере были отключены!");
        }
    }

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
