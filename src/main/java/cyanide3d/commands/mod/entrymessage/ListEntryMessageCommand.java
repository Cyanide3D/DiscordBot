package cyanide3d.commands.mod.entrymessage;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.EntryMessageService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;

import java.util.Map;

public class ListEntryMessageCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public ListEntryMessageCommand() {
        this.name = "listentrymessage";
        this.aliases = new String[]{"lem"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        EntryMessageService service = EntryMessageService.getInstance();
        final Map<String, String> entryMessages = service.getAllForGuild(event.getGuild().getId());

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
