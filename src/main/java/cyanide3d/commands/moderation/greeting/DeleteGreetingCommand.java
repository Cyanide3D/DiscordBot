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
        if (event.getArgs().split(" ").length != 1) {
            event.reply("Проверьте правильность введёных аргументов.");
            return;
        }

        GreetingService service = GreetingService.getInstance();
        try {
            service.delete(event.getArgs(), event.getGuild().getId());
        } catch (NoSuchElementException e) {
            event.reply("Сообщения с таким ключём не существует.");
            return;
        }
        event.reply("Сообщение удалено.");
    }
}
