package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.util.LevelTemplateCreator;
import cyanide3d.dto.UserEntity;
import cyanide3d.service.PermissionService;
import cyanide3d.service.UserService;
import cyanide3d.util.Permission;

import java.io.File;

public class LevelCommand extends Command {

    private UserService userService;

    public LevelCommand() {
        this.name = "level";
        this.help = "Просмотр уровня.";
    }

    @Override
    protected void execute(CommandEvent event) {
        userService = UserService.getInstance();
        LevelTemplateCreator makeLevelTemplateCreator = new LevelTemplateCreator();
        final UserEntity user = userService.getUser(event.getAuthor().getId(), event.getGuild().getId());
        String avatarUrl = event.getAuthor().getAvatarUrl();
        String username = event.getAuthor().getAsTag();
        makeLevelTemplateCreator.makeTemplate(username, user.getLvl(), user.getExp(), avatarUrl, chooseTemplateName(event));
        event.reply(new File("picture\\output.png"), "output.png");
    }

    private String chooseTemplateName(CommandEvent event){
        return PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())
                ? "templateMod"
                : "templateUser";
    }
}