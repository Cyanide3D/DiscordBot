package cyanide3d.commands.moderation.defaultroles;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.DefaultRoleService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;

import java.util.NoSuchElementException;

public class DeleteDefaultRoleCommand extends Command {

    private final Localization localization = Localization.getInstance();
    private final int REQUIRED_ARGS_SIZE = 1;

    public DeleteDefaultRoleCommand() {
        this.name = "deletedefaultrole";
        this.aliases = new String[]{"ddr"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        int argsAmount = event.getArgs().split(" ").length;
        DefaultRoleService service = DefaultRoleService.getInstance();

        if (argsAmount != REQUIRED_ARGS_SIZE) {
            event.reply("Проверьте правильность введёных аргументов.");
        }

        try {
            service.deleteRole(event.getArgs(), event.getGuild().getId());
        } catch (NoSuchElementException e) {
            System.out.println("Что то пошло не так.");
            return;
        }
        event.reply("Роль удалена.");

    }
}
