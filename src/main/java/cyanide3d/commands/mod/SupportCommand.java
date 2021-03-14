package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.dto.ActionEntity;
import cyanide3d.service.ActionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.List;

public class SupportCommand extends Command {

    public SupportCommand() {
        this.name = "sc";
    }


    @Override
    protected void execute(CommandEvent event) {
        ActionService service = ActionService.getInstance();
        final List<ActionEntity> actionEntities = service.listByGuildId(event.getGuild().getId());
        System.out.println(actionEntities.size());
        actionEntities.forEach(actionEntity -> System.out.println(actionEntity.getAction()));
    }
}
