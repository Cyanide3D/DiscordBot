package cyanide3d.commands.moderation.badwords;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.repository.service.SpeechService;
import cyanide3d.util.Permission;

public class BadwordAddCommand extends Command {

    private final Localization localization = Localization.getInstance();
    private final SpeechService service;

    public BadwordAddCommand(){
        service = SpeechService.getInstance();
        this.name = "addword";
        this.aliases = new String[]{"addbadword"};
        this.arguments = "[word]";
        this.help = localization.getMessage("addbadword.help");
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().contains(" ")) {
            event.reply("Неправильный синтаксис команды!");
        } else {
            service.addBadWord(event.getArgs().toLowerCase(), event.getGuild().getId());
            event.reply(localization.getMessage("addbadword.successfully"));
        }
    }
}
