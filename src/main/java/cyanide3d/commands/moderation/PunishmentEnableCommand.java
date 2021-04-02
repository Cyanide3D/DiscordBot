package cyanide3d.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.exceptions.PunishmentDuplicateException;
import cyanide3d.exceptions.PunishmentNotFoundException;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.repository.service.PunishmentService;
import cyanide3d.util.Permission;
import cyanide3d.util.Punishment;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class PunishmentEnableCommand extends Command {
    private final Localization localization;
    private final Punishment punishment;

    public PunishmentEnableCommand() {
        punishment = new Punishment();
        localization = Localization.getInstance();
        this.name = "punishment";
    }

    //SYNTAX - $punishment [enable\disable] [violationsBeforeMute] [punishment time] [link role]
    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        if (event.getArgs().equals("disable")) {
            disable(event);
            return;
        }

        String[] args = event.getArgs().split(" ");
        List<Role> mentionedRoles = event.getMessage().getMentionedRoles();

        if (args.length != 4 || mentionedRoles.size() != 1) {
            event.reply("Ошибка в синтаксисе команды.");
            return;
        }

        enable(event, mentionedRoles.get(0).getId(), args);
    }

    private void enable(CommandEvent event, String roleId, String[] args) {
        try {
            punishment.enable(event.getGuild().getId(), Integer.parseInt(args[1]), roleId, Integer.parseInt(args[2]));
            event.reply("Наказания успешно включены!");
        } catch (PunishmentDuplicateException e) {
            event.reply("Наказания уже включены для этого сервера!");
        }
    }

    private void disable(CommandEvent event) {
        try {
            punishment.disable(event.getGuild().getId());
            event.reply("Наказания успешно отключены!");
        } catch (PunishmentNotFoundException e) {
            event.reply("Наказания на сервере были отключены!");
        }
    }

}
