package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class HelpCommand extends Command {

    private final Localization localization;
    private final CommandClientManager commandClientManager;
    private final String prefix;

    public HelpCommand() {
        localization = Localization.getInstance();
        commandClientManager = CommandClientManager.getInstance();
        prefix = commandClientManager.getPrefix();
        this.name = "help";
    }

    @Override
    protected void execute(CommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();

        writeHelpForModerators(builder, event);
        writeHelpForAll(builder);

        event.reply(builder.build());
    }

    private void writeHelpForAll(EmbedBuilder builder) {
        builder.setTitle(localization.getMessage("command.help.title"))
                .setColor(Color.ORANGE)
                .addField(localization.getMessage("command.help.field.all.title"), localization.getMessage("command.help.field.all.text", prefix), false)
                .addField(localization.getMessage("command.help.field.fun.title"), localization.getMessage("command.help.field.fun.text", prefix), false)
                .addField(localization.getMessage("command.help.field.music.title"), localization.getMessage("command.help.field.music.text", prefix), false)
                .build();
    }

    private void writeHelpForModerators(EmbedBuilder builder, CommandEvent event) {
        if (isModerator(event)) {
            builder.addField(localization.getMessage("command.help.field.moderator.title"), localization.getMessage("command.help.field.moderator.text", prefix), false);
        }
    }

    private boolean isModerator(CommandEvent event) {
        return PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId());
    }

}
