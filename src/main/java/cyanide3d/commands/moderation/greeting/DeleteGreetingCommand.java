package cyanide3d.commands.moderation.greeting;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.GreetingService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;

import java.util.NoSuchElementException;

public class DeleteGreetingCommand extends Command {

    private final Localization localization = Localization.getInstance();
    private final int REQUIRED_ARGS_SIZE = 1;
    private final String ARGS_SEPARATOR = " ";

    public DeleteGreetingCommand() {
        this.name = "deletegreeting";
        this.aliases = new String[]{"dg"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().split(ARGS_SEPARATOR).length != REQUIRED_ARGS_SIZE) {
            event.reply("Проверьте правильность введёных аргументов.");
            return;
        }

        deleteGreeting(event);

    }

    private void deleteGreeting(CommandEvent event) {
        GreetingService service = GreetingService.getInstance();
        try {
            service.deleteGreetingByKey(event.getArgs(), event.getGuild().getId());
            event.reply("Сообщение удалено.");
        } catch (NoSuchElementException e) {
            event.reply("Сообщения с таким ключём не существует.");
        }
    }
}
