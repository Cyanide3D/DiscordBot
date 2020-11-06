package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.PermissionService;

import java.util.Locale;
import java.util.ResourceBundle;

public class RemoveMessages extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public RemoveMessages() {
        this.name = "clear";
        this.aliases = new String[]{"clearmessage"};
        this.arguments = "[count]";
        this.help = localization.getMessage("clear.help");
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        event.getChannel().getIterableHistory().takeAsync(Integer.parseInt(event.getArgs()) + 1).thenAccept(event.getChannel()::purgeMessages);
        event.reply(localization.getMessage("clear.successfully",event.getArgs()));
    }
}
