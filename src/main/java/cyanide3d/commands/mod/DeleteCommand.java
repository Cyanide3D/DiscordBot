package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Config;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.service.PermissionService;

public class DeleteCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public DeleteCommand() {
        this.name = "removecommand";
    }
    @Override
    protected void execute(CommandEvent event) {
        if (!new PermissionService(PermissionEntity.class, event.getGuild().getId()).checkPermission(event.getMember(), Permission.ADMIN)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if(!event.getArgs().startsWith(Config.getInstance().getPrefix()) || event.getArgs().split(" ").length > 1 || event.getArgs().isEmpty()){
            event.reply("Неправильный аргумент!");
            return;
        }
        CommandClientManager.getInstance().deleteCommand(event.getArgs().substring(1));
        event.reply("Команда успешно удалена!");
    }
}
