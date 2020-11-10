package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.service.PermissionService;

public class AddCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public AddCommand() {
        this.name = "addcommand";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.ADMIN)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        String[] args = event.getArgs().split(" ");
        if(!args[0].startsWith("$") || args.length < 2){
            event.reply("Не правильный аргумент!");
            return;
        }
        CommandClientManager.getInstance().createCommand(args[0].substring(1), event.getArgs().replace(args[0],""));
        event.reply("Команда успешно добавлена!");
    }
}
