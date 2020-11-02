package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.DatabaseConnection;
import cyanide3d.conf.Permission;
import cyanide3d.conf.UserAccessToCommand;
import cyanide3d.service.BadWordsService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class RemoveBadWords extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public RemoveBadWords() {
        this.name = "removeword";
        this.aliases = new String[]{"removebadword"};
        this.arguments = "[word]";
        this.help = localization.getMessage("removeword.help");
    }

    @Override
    protected void execute(CommandEvent event) {
        UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
        if (!userAccess.getAccess(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        try {
            if (BadWordsService.getInstance().getBadWords().contains(event.getArgs())) {
                new DatabaseConnection().removeBadWord(event.getArgs());
                BadWordsService.getInstance().updateBadWords();
                event.reply(localization.getMessage("removeword.successfully"));
                return;
            }
        } catch (SQLException throwables) {
            event.reply(localization.getMessage("deniedAccessBD"));
        }
    }
}
