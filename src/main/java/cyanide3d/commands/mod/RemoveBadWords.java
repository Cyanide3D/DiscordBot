package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.BadWordsService;
import cyanide3d.service.PermissionService;

public class RemoveBadWords extends Command {

    private final Localization localization = Localization.getInstance();

    public RemoveBadWords() {
        this.name = "removeword";
        this.aliases = new String[]{"removebadword"};
        this.arguments = "[word]";
        this.help = localization.getMessage("removeword.help");
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().contains(" ")) {
            event.reply("NIPANYATNO");
        } else {
            BadWordsService.getInstance().remove(event.getArgs());
            event.reply(localization.getMessage("badwords.remove.success"));
        }
    }
}
