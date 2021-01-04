package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.actions.ExpTemplateAction;
import cyanide3d.conf.Permission;
import cyanide3d.model.User;
import cyanide3d.service.PermissionService;
import cyanide3d.service.UserService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

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
        String username = event.getAuthor().getAsTag();
        makeExpTemplateAction.makeTemplate(username, user.getLevel(), user.getExperience(), avatarUrl, chooseTemplateName(event.getMember()));
        event.reply(new File("picture\\output.png"), "output.png");
    }

    private String chooseTemplateName(Member user){
        return PermissionService.getInstance().checkPermission(user, Permission.MODERATOR) ? "templateMod" : "templateUser";
    }
}