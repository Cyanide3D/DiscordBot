package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.util.LevelTemplateCreator;
import cyanide3d.repository.model.UserEntity;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.repository.service.UserService;
import cyanide3d.util.Permission;

import java.io.File;
import java.util.NoSuchElementException;

public class LevelCommand extends Command {

    private final UserService userService = UserService.getInstance();
    private final String USER_TEMPLATE_NAME = "templateUser";
    private final String MODERATOR_TEMPLATE_NAME = "templateMod";

    public LevelCommand() {
        this.name = "level";
        this.help = "Просмотр уровня.";
    }

    @Override
    protected void execute(CommandEvent event) {
        LevelTemplateCreator makeLevelTemplateCreator = new LevelTemplateCreator();

        UserEntity user = getUser(event);

        makeLevelTemplateCreator.makeTemplate(
                event.getAuthor().getAsTag(),
                user.getLvl(), user.getExp(),
                event.getAuthor().getAvatarUrl(),
                chooseTemplateName(event)
        );
        event.reply(new File("picture\\output.png"), "output.png");
    }

    private UserEntity getUser(CommandEvent event) {
        try {
            return userService.getUserById(event.getAuthor().getId(), event.getGuild().getId());
        } catch (NoSuchElementException e) {
            event.reply("Что то пошло не так.");
            return new UserEntity();
        }
    }

    private String chooseTemplateName(CommandEvent event) {
        return PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())
                ? MODERATOR_TEMPLATE_NAME
                : USER_TEMPLATE_NAME;
    }
}