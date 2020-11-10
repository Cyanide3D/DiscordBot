package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.BadWordsService;
import cyanide3d.service.PermissionService;

public class AddBadWord extends Command {

    private final Localization localization = Localization.getInstance();

    public AddBadWord(){
        this.name = "addword";
        this.aliases = new String[]{"addbadword"};
        this.arguments = "[word]";
        this.help = localization.getMessage("addbadword.help");
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().contains(" ")) {
            event.reply("Неправильный синтаксис команды!");
        } else {
            BadWordsService.getInstance().add(event.getArgs().toLowerCase());
            event.reply(localization.getMessage("addbadword.successfully"));
        }
    }
}
