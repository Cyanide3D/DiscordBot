package cyanide3d.commands.moderation.blacklist;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.exceptions.IncorrectInputDataException;
import cyanide3d.repository.service.BlacklistService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;

public class BlacklistReleaseCommand extends Command {

    private final BlacklistService service = BlacklistService.getInstance();
    private final Localization localization = Localization.getInstance();

    public BlacklistReleaseCommand() {
        this.name = "pardon";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        if (event.getArgs().split(" ").length != 1) {
            localization.getMessage("command.incorrect.syntax");
            return;
        }

        release(event);

    }

    private void release(CommandEvent event) {
        try {
            service.deleteFromBlacklistById(event.getArgs(), event.getGuild().getId());
            event.reply(localization.getMessage("command.blacklist.release.success"));
        } catch (IncorrectInputDataException e) {
            event.reply(localization.getMessage("command.blacklist.release.failed"));
        }
    }

}
