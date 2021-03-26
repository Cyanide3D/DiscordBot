package cyanide3d.commands.moderation.greeting;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.GreetingService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;

import java.util.Map;

public class ListGreetingCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public ListGreetingCommand() {
        this.name = "listgreeting";
        this.aliases = new String[]{"lg"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        GreetingService service = GreetingService.getInstance();
        final Map<String, String> entryMessages = service.getGreetingsAndKeysByGuild(event.getGuild().getId());

        if (entryMessages.isEmpty()) {
            event.reply("Список сообщений пуст.");
            return;
        }

        StringBuilder builder = new StringBuilder();
        entryMessages.forEach((k, v) ->
                builder
                        .append("--------------------------")
                        .append("\n**КЛЮЧ:**\n")
                        .append(k)
                        .append("\n**СООБЩЕНИЕ:**\n")
                        .append(v)
                        .append("\n")
        );
        event.reply(builder.toString());
    }
}
