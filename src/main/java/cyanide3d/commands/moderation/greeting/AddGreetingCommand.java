package cyanide3d.commands.moderation.greeting;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.GreetingService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import org.apache.commons.lang3.StringUtils;

public class AddGreetingCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public AddGreetingCommand() {
        this.name = "addgreeting";
        this.aliases = new String[]{"ag"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        String[] args = event.getArgs().split(" ");

        if (args.length < 2) {
            event.reply("Проверьте правильность введёных аргументов.");
            return;
        }

        GreetingService service = GreetingService.getInstance();
        service.addGreetingByKey(args[0], StringUtils.substringAfter(event.getArgs(), args[0] + " "), event.getGuild().getId());

        event.reply("Сообщение успешно добавлено!");
    }
}
