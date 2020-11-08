package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Permission;
import cyanide3d.service.PermissionService;

public class Help extends Command {
    public Help(){
        this.name = "help";
    }

    @Override
    protected void execute(CommandEvent event) {
        EmbedTemplates embedTemplates = new EmbedTemplates();
        event.reply(embedTemplates.HELP_ALL);
        if(PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)){
            event.reply(embedTemplates.HELP_MOD);
        }
    }
}
