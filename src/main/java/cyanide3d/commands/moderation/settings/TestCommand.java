package cyanide3d.commands.moderation.settings;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.repository.service.PunishmentService;
import cyanide3d.util.Punishment;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.apache.maven.shared.utils.logging.MessageUtils;

public class TestCommand extends Command {

    public TestCommand() {
        this.name = "sc";
    }


    @Override
    protected void execute(CommandEvent event) {
    }
}
