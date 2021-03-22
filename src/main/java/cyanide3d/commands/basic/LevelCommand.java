package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.util.LevelTemplateCreator;
import cyanide3d.dto.UserEntity;
import cyanide3d.service.PermissionService;
import cyanide3d.service.UserService;
import cyanide3d.util.Permission;

import java.io.File;
import java.util.NoSuchElementException;

public class LevelCommand extends Command {

    private final UserService userService = UserService.getInstance();

    public LevelCommand() {
        this.name = "level";
        this.help = "Просмотр уровня.";
    }

    @Override
    protected void execute(CommandEvent event) {
        LevelTemplateCreator makeLevelTemplateCreator = new LevelTemplateCreator();
        UserEntity user;

        try {
            user = userService.getUser(event.getAuthor().getId(), event.getGuild().getId());
        } catch (NoSuchElementException e) {
            event.reply("Что то пошло не так.");
            return;
        }

        makeLevelTemplateCreator.makeTemplate(
                event.getAuthor().getAsTag(),
                user.getLvl(), user.getExp(),
                event.getAuthor().getAvatarUrl(),
                chooseTemplateName(event)
        );
        event.reply(new File("picture\\output.png"), "output.png");
    }

    private String chooseTemplateName(CommandEvent event) {
        return PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())
                ? "templateMod"
                : "templateUser";
    }
}