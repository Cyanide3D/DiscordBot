package cyanide3d.commands.moderation.customcommands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.Configuration;
import cyanide3d.exceptions.CommandDuplicateException;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;

public class AddCustomCommand extends Command {

    private final Localization localization = Localization.getInstance();
    private final int MIN_ARGS_SIZE = 2;
    private final int COMMAND_INDEX = 0;
    private final String ARGS_SEPARATOR = " ";

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

        String[] args = event.getArgs().split(ARGS_SEPARATOR);
        if (!args[COMMAND_INDEX].startsWith(Configuration.getInstance().getPrefix()) || args.length < MIN_ARGS_SIZE) {
            event.reply("Не правильный аргумент!");
            return;
        }

        createCommand(event, args);

    }

    private void createCommand(CommandEvent event, String[] args) {
        CommandClientManager commandClientManager = CommandClientManager.getInstance();
        try {
            commandClientManager.createCommand(args[COMMAND_INDEX].substring(1), event.getArgs().replace(args[COMMAND_INDEX], ""), event.getGuild().getId());
            event.reply("Команда успешно добавлена!");
        } catch (CommandDuplicateException e) {
            event.reply("Такая команда уже существует!");
        }
    }

}
