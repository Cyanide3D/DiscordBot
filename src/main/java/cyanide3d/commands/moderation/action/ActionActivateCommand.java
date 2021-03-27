package cyanide3d.commands.moderation.action;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.ActionService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.ActionType;
import cyanide3d.util.Permission;

import java.util.Set;

public class ActionActivateCommand extends Command {
    private final Localization localization = Localization.getInstance();
    private final static Set<String> availableStates;
    private final String ARGS_SEPARATOR = " ";
    private final int REQUIRED_ARGS_SIZE = 2;
    private final int ENABLED_INDEX = 1;
    private final int ACTION_INDEX = 0;

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
        String[] args = event.getArgs().split(ARGS_SEPARATOR);
        if (event.getArgs().isEmpty() || args.length != REQUIRED_ARGS_SIZE) {
            event.reply("Синтаксис команды:" +
                    "\n`$activate [action] [true|false]`");
            return;
        }
        final String action = args[ACTION_INDEX].toLowerCase();
        final String enabled = args[ENABLED_INDEX].toLowerCase();
        if (!availableStates.contains(enabled)) {
            event.reply("Состояние " + enabled + " не поддерживается.");
            return;
        }
        functionEnable(event, action, enabled);
    }

    private void functionEnable(CommandEvent event, String action, String enabled) {
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
