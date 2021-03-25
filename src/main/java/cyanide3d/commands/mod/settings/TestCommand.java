package cyanide3d.commands.mod.settings;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.dto.ActionEntity;
import cyanide3d.service.ActionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.Date;
import java.util.List;

public class TestCommand extends Command {

    public TestCommand() {
        this.name = "sc";
    }


    @Override
    protected void execute(CommandEvent event) {

    }
}
