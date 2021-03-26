package cyanide3d.commands.moderation.settings;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;

public class SetPrefixCommand extends Command {

    private final Localization localization = Localization.getInstance();
    final String[] availablePrefixes = {"$", ",", "+", "!"};

    public SetPrefixCommand() {
        this.name = "setprefix";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.ADMIN, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().isEmpty() || !isAvailablePrefix(event.getArgs())) {
            event.reply("Некорректный префикс.");
            return;
        }
        //CommandClientManager.getInstance(event.getJDA()).setPrefix(event.getArgs());
        event.reply("Префикс успешно установлен!");
    }

    private boolean isAvailablePrefix(String prefix) {
        for (String availablePrefix : availablePrefixes) {
            if (availablePrefix.equalsIgnoreCase(prefix)) return true;
        }
        return false;
    }
}
