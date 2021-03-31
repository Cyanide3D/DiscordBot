package cyanide3d.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.repository.service.UserService;
import cyanide3d.util.Permission;

import java.util.NoSuchElementException;

public class SetLevelCommand extends Command {

    private final Localization localization;
    private final UserService userService;

    public SetLevelCommand() {
        this.name = "setlvl";
        localization = Localization.getInstance();
        userService = UserService.getInstance();
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getAuthor().getId().equals("534894366448156682")) {
            String[] args = event.getArgs().split(" ");

            String userId = args[0];
            int level = Integer.parseInt(args[1]);

            setLevel(userId, event, level);
        } else {
            event.reply(localization.getMessage("accessDenied", name));
        }
    }

    private void setLevel(String userId, CommandEvent event, int level) {
        try {
            userService.setUserLevel(userId, event.getGuild().getId(), level);
            event.reply("Success");
        } catch (NoSuchElementException ignore) {
            event.reply("Is there something wrong");
        }
    }

}
