package cyanide3d.commands.moderation.settings;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.repository.service.PunishmentService;

public class TestCommand extends Command {

    public TestCommand() {
        this.name = "sc";
    }


    @Override
    protected void execute(CommandEvent event) {
        PunishmentService service = PunishmentService.getInstance();
//        service.enable(event.getGuild().getId(), 2, "123", 10);
        service.increaseViolation(event.getGuild().getId(), event.getAuthor().getId());
    }
}
