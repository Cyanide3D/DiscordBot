package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.actions.ExpTemplateAction;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.dto.UserEntity;
import cyanide3d.util.Permission;
import cyanide3d.model.User;
import cyanide3d.service.PermissionService;
import cyanide3d.service.UserService;
import net.dv8tion.jda.api.entities.Member;

import java.io.File;

public class UserLvl extends Command {

    private UserService userService;

    public UserLvl() {
        this.name = "level";
        this.help = "Просмотр уровня.";
    }

    @Override
    protected void execute(CommandEvent event) {
        userService = new UserService(UserEntity.class, event.getGuild().getId());
        ExpTemplateAction makeExpTemplateAction = new ExpTemplateAction();
        final UserEntity user = userService.getUser(event.getAuthor().getId());
        String avatarUrl = event.getAuthor().getAvatarUrl();
        String username = event.getAuthor().getAsTag();
        makeExpTemplateAction.makeTemplate(username, user.getLvl(), user.getExp(), avatarUrl, chooseTemplateName(event));
        event.reply(new File("picture\\output.png"), "output.png");
    }

    private String chooseTemplateName(CommandEvent event){
        return new PermissionService(PermissionEntity.class, event.getGuild().getId()).checkPermission(event.getMember(), Permission.MODERATOR) ? "templateMod" : "templateUser";
    }
}