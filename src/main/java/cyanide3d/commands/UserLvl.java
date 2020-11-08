package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.actions.ExpTemplateAction;
import cyanide3d.model.User;
import cyanide3d.service.UserService;

import java.io.File;

public class UserLvl extends Command {

    private final UserService userService;

    public UserLvl() {
        this.name = "level";
        this.help = "Просмотр уровня.";
        userService = UserService.getInstance();
    }

    @Override
    protected void execute(CommandEvent event) {
        ExpTemplateAction makeExpTemplateAction = new ExpTemplateAction();
        final User user = userService.getUser(event.getAuthor().getId());
        String avatarUrl = event.getAuthor().getAvatarUrl();
        String username = event.getAuthor().getName();
        makeExpTemplateAction.makeTemplate(username, user.getLevel(), user.getExperience(), avatarUrl);
        event.reply(new File("picture\\output.png"), "output.png");
    }
}