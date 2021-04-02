package cyanide3d.commands.moderation.settings;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.repository.service.PunishmentService;
import cyanide3d.util.Punishment;

public class TestCommand extends Command {

    public TestCommand() {
        this.name = "sc";
    }


    @Override
    protected void execute(CommandEvent event) {
//        PunishmentService punishmentService = PunishmentService.getInstance();
//        punishmentService.enable(event.getGuild().getId(), 2, "818207085102628885", 1);
//        punishmentService.increaseViolation(event.getGuild().getId(), event.getAuthor().getId());
        Punishment punishment = new Punishment();
        punishment.punish(event.getGuild(), event.getMember());
    }
}
