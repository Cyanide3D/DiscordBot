package cyanide3d.commands.mod.customcommands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.CustomCommandService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;

public class ListCustomCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public ListCustomCommand() {
        this.name = "listcommand";
        this.aliases = new String[]{"lcmd"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        CustomCommandService service = CustomCommandService.getInstance();
        String commands = service.getCommandNameList(event.getGuild().getId());

        MessageEmbed builder = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setTitle("Список команд.")
                .addField("", commands, false)
                .build();

        event.reply(builder);
    }
}
