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

    public PunishmentEnableCommand() {
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
            Punishment.disable(event);
            return;
        }

        String[] args = event.getArgs().split(" ");
        List<Role> mentionedRoles = event.getMessage().getMentionedRoles();

        if (args.length != 4 || mentionedRoles.size() != 1) {
            event.reply("Ошибка в синтаксисе команды.");
            return;
        }

        Punishment.enable(event, mentionedRoles.get(0).getId(), args);
    }
}
