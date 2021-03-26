package cyanide3d.commands.moderation.customcommands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.Configuration;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;

public class DeleteCustomCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public DeleteCustomCommand() {
        this.name = "removecommand";
        this.aliases = new String[]{"rcmd"};
    }
    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.ADMIN, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if(!event.getArgs().startsWith(Configuration.getInstance().getPrefix()) || event.getArgs().split(" ").length > 1 || event.getArgs().isEmpty()){
            event.reply("Не правильный аргумент!");
            return;
        }
        CommandClientManager commandClientManager = CommandClientManager.getInstance();
        try {
            commandClientManager.deleteCommand(event.getArgs().substring(1), event.getGuild().getId());
        } catch (IllegalArgumentException e) {
            event.reply("Такой команды не существует.");
            return;
        }
        event.reply("Команда успешно удалена!");
    }
}