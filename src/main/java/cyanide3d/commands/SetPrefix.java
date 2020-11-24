package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
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
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.ADMIN)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().equals("") || !checkPrefix(event.getArgs())) {
            event.reply("Некорректный префикс.");
            return;
        }
        CommandClientManager.getInstance().setPrefix(event.getArgs());
        event.reply("Префикс успешно установлен!");
    }

    private boolean checkPrefix(String prefix) {
        for (String availablePrefix : availablePrefixes) {
            if (availablePrefix.equalsIgnoreCase(prefix)) return true;
        }
        return false;
    }
}
