package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

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
        builder.setTitle("Доступные команды бота:")
                .setColor(Color.ORANGE)
                //TODO имена тоже выкинуть в локаль
                .addField(":bulb:Основные команды:bulb:", localization.getMessage("help.all.text", prefix), false)
                .addField(":game_die:Фановые команды:game_die:", localization.getMessage("help.fun.text", prefix), false)
                .addField(":musical_note:Команды для воспроизведения музыки:musical_note:", localization.getMessage("help.music.text", prefix), false)
                .build();
    }

    private void writeHelpForModerators(EmbedBuilder builder, CommandEvent event) {
        if (isModerator(event)) {
            builder.addField(":octagonal_sign:Команды для модерирования:octagonal_sign:", localization.getMessage("help.mod.text", prefix), false);
        }
    }

    private boolean isModerator(CommandEvent event) {
        return PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId());
    }

}
