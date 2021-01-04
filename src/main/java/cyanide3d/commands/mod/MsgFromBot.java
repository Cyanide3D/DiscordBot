package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Config;
import cyanide3d.conf.Permission;
import cyanide3d.service.PermissionService;

public class MsgFromBot extends Command {

    private final Localization localization = Localization.getInstance();

    public MsgFromBot() {
        this.name = "msg";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.ADMIN)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        event.getMessage().delete().queue();
        event.reply(event.getMessage().getContentRaw().replaceAll( Config.getInstance().getPrefix() + "msg ", ""));
    }
}
