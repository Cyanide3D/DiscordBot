package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;

public class MessageCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public MessageCommand() {
        this.name = "msg";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.ADMIN, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        event.getMessage().delete().queue();
        //event.getMessage().getContentRaw().replace( Config.getInstance().getPrefix() + "msg ", "")
        event.reply(event.getArgs());
    }
}
