package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import cyanide3d.service.PermissionService;

public class MsgFromBot extends Command {

    private final Localization localization = Localization.getInstance();

    public MsgFromBot() {
        this.name = "msg";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!new PermissionService(PermissionEntity.class, event.getGuild().getId()).checkPermission(event.getMember(), Permission.ADMIN)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        event.getMessage().delete().queue();
        //event.getMessage().getContentRaw().replace( Config.getInstance().getPrefix() + "msg ", "")
        event.reply(event.getArgs());
    }
}
