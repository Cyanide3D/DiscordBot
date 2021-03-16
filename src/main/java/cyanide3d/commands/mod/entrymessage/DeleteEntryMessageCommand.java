package cyanide3d.commands.mod.entrymessage;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.EntryMessageService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;

public class DeleteEntryMessageCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public DeleteEntryMessageCommand() {
        this.name = "deleteentrymessage";
        this.aliases = new String[]{"dem"};
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

        EntryMessageService service = EntryMessageService.getInstance();
        service.delete(event.getArgs(), event.getGuild().getId());
        event.reply("Если сообщение под таким ключём существовало, то оно было удалено.");
    }
}
