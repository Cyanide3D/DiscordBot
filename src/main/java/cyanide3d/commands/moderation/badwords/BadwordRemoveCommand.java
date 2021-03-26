package cyanide3d.commands.moderation.badwords;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.PermissionService;
import cyanide3d.service.SpeechService;
import cyanide3d.util.Permission;

public class BadwordRemoveCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public BadwordRemoveCommand() {
        this.name = "removeword";
        this.aliases = new String[]{"removebadword"};
        this.arguments = "[word]";
        this.help = localization.getMessage("removeword.help");
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().contains(" ")) {
            event.reply("NIPANYATNO");
        } else {
           SpeechService.getInstance().remove(event.getArgs(), event.getGuild().getId());
            event.reply(localization.getMessage("badwords.remove.success"));
        }
    }
}
