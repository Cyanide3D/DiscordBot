package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.listener.CommandListener;
import cyanide3d.service.PermissionService;

import java.util.Locale;

public class DeleteCommand extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public DeleteCommand() {
        this.name = "removecommand";
    }
    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.ADMIN)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if(!event.getArgs().startsWith("$") || event.getArgs().split(" ").length > 1 || event.getArgs().isEmpty()){
            event.reply("Не правильный аргумент!");
            return;
        }
        CommandListener.getInstance().setEvent(event).deleteCommand(event.getArgs().substring(1));
        event.reply("Команда успешно удалена!");
    }
}
