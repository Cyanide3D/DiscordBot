package cyanide3d.commands.mod.action;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.ActionService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.ActionType;
import cyanide3d.util.Permission;

import java.util.Set;

public class ActionActivateCommand extends Command {
    private final Localization localization = Localization.getInstance();
    private final static Set<String> availableStates;

    public ActionActivateCommand() {
        this.name = "activate";
    }

    static {
        availableStates = Set.of("true", "false");
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        String[] args = event.getArgs().split(" ");
        if (args.length != 2) {
            event.reply("Неправильный синтаксис команды!");
            return;
        }
        final String action = args[0].toLowerCase();
        final String enabled = args[1].toLowerCase();
        if (!availableStates.contains(enabled)) {
            event.reply("Состояние [" + enabled + "] не поддерживается.");
            return;
        }
        try {
           ActionService.getInstance().enable(ActionType.valueOf(action.toUpperCase()), Boolean.parseBoolean(enabled), event.getGuild().getId());
            event.reply("Состояние функции успешно обновлено!");
        } catch (ArrayIndexOutOfBoundsException exe) {
            event.reply("Нужно больше аргументов.");
        } catch (IllegalArgumentException e) {
            event.reply("Указанная функция не поддерживается.");
        }
    }
}
