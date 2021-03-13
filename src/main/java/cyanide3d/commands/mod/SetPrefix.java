package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.service.PermissionService;

public class SetPrefix extends Command {

    private final Localization localization = Localization.getInstance();
    final String[] availablePrefixes = {"$", ",", "+", "!"};

    public SetPrefix() {
        this.name = "setprefix";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!new PermissionService(PermissionEntity.class, event.getGuild().getId()).checkPermission(event.getMember(), Permission.ADMIN)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().isEmpty() || !isAvailablePrefix(event.getArgs())) {
            event.reply("Некорректный префикс.");
            return;
        }
        CommandClientManager.getInstance().setPrefix(event.getArgs());
        event.reply("Префикс успешно установлен!");
    }

    private boolean isAvailablePrefix(String prefix) {
        for (String availablePrefix : availablePrefixes) {
            if (availablePrefix.equalsIgnoreCase(prefix)) return true;
        }
        return false;
    }
}
