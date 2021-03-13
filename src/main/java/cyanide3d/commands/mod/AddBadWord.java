package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.BadwordEntity;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import cyanide3d.service.SpeechService;
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
        if (!new PermissionService(PermissionEntity.class, event.getGuild().getId()).checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().contains(" ")) {
            event.reply("Неправильный синтаксис команды!");
        } else {
            new SpeechService(BadwordEntity.class, event.getGuild().getId()).add(event.getArgs().toLowerCase());
            event.reply(localization.getMessage("addbadword.successfully"));
        }
    }
}
