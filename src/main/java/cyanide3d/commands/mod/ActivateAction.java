package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.ActionEntity;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.ActionType;
import cyanide3d.util.Permission;
import cyanide3d.service.ActionService;
import cyanide3d.service.PermissionService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ActivateAction extends Command {
    private final Localization localization = Localization.getInstance();
    private final static Set<String> availableStates;

    public ActivateAction() {
        this.name = "activate";
    }

    static {
        final Set<String> states = new HashSet<>(2);
        states.add("true");
        states.add("false");
        availableStates = Collections.unmodifiableSet(states);
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!new PermissionService(PermissionEntity.class, event.getGuild().getId()).checkPermission(event.getMember(), Permission.MODERATOR)) {
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
            new ActionService(ActionEntity.class, event.getGuild().getId()).enable(ActionType.valueOf(action), Boolean.parseBoolean(enabled));
            event.reply("Состояние функции успешно обновлено!");
        } catch (ArrayIndexOutOfBoundsException exe) {
            event.reply("Нужно больше аргументов.");
        }
    }
}
