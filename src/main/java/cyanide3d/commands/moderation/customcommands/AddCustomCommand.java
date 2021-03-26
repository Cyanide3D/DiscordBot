package cyanide3d.commands.moderation.customcommands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.Configuration;
import cyanide3d.exceptions.CommandDuplicateException;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;

public class AddCustomCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public AddCustomCommand() {
        this.name = "addcommand";
        this.aliases = new String[]{"acmd"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        String[] args = event.getArgs().split(" ");
        if(!args[0].startsWith(Configuration.getInstance().getPrefix()) || args.length < 2){
            event.reply("Не правильный аргумент!");
            return;
        }
        CommandClientManager commandClientManager = CommandClientManager.getInstance();
        try {
            commandClientManager.createCommand(args[0].substring(1), event.getArgs().replace(args[0],""), event.getGuild().getId());
        } catch (CommandDuplicateException e) {
            event.reply("Такая команда уже существует!");
            return;
        }
        event.reply("Команда успешно добавлена!");
    }
}
