package cyanide3d.commands.mod.entryrole;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.EntryRoleEntity;
import cyanide3d.service.EntryRoleService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;

import java.util.NoSuchElementException;

public class DeleteEntryRoleCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public DeleteEntryRoleCommand() {
        this.name = "deleteentryrole";
        this.aliases = new String[]{"der"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        int argsAmount = event.getArgs().split(" ").length;
        EntryRoleService service = EntryRoleService.getInstance();

        if (argsAmount != 1) {
            event.reply("Проверьте правильность введёных аргументов.");
        }

        try {
            service.delete(event.getArgs(), event.getGuild().getId());
        } catch (NoSuchElementException e) {
            System.out.println("Что то пошло не так.");
            return;
        }
        event.reply("Роль удалена.");

    }
}
