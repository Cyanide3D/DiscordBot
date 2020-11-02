package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.conf.UserAccessToCommand;
import cyanide3d.service.BadWordsService;
import cyanide3d.dao.DatabaseConnection;

import java.sql.SQLException;
import java.util.Locale;

public class AddBadWord extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public AddBadWord(){
        this.name = "addword";
        this.aliases = new String[]{"addbadword"};
        this.arguments = "[word]";
        this.help = localization.getMessage("addbadword.help");
    }

    @Override
    protected void execute(CommandEvent event) {
        UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
        if (!userAccess.getAccess(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().contains(" ")) {
            event.reply(localization.getMessage("badwords.add.malformed"));
        } else {
            BadWordsService.getInstance().add(event.getArgs());
            event.reply(localization.getMessage("badwords.add.success"));
        }
    }
}
