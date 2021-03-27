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
    private final GreetingService service;

    public ListGreetingCommand() {
        service = GreetingService.getInstance();
        this.name = "listgreeting";
        this.aliases = new String[]{"lg"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        final Map<String, String> greetings = service.getGreetingsAndKeysByGuild(event.getGuild().getId());

        if (greetings.isEmpty()) {
            event.reply("Список сообщений пуст.");
            return;
        }
        event.reply(parseMapInString(greetings));
    }

    private String parseMapInString(Map<String, String> greetings) {
        StringBuilder builder = new StringBuilder();
        greetings.forEach((k, v) ->
                builder.append(String.format("--------------------------" +
                        "\n**КЛЮЧ:**\n%s\n**СООБЩЕНИЕ:**\n%s\n", k, v))
        );

        return builder.toString();
    }

}
