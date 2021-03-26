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

    private final Localization localization = Localization.getInstance();

    public HelpCommand(){
        this.name = "help";
    }

    @Override
    protected void execute(CommandEvent event) {
        CommandClientManager commandClientManager = CommandClientManager.getInstance();
        String prefix = commandClientManager.getPrefix();
        MessageEmbed helpAll = new EmbedBuilder()
                .setTitle("Доступные команды бота:")
                .setColor(Color.ORANGE)
                //TODO имена тоже выкинуть в локаль
                .addField(":bulb:Основные команды:bulb:", localization.getMessage("help.all.text", prefix), false)
                .addField(":game_die:Фановые команды:game_die:", localization.getMessage("help.fun.text", prefix), false)
                .addField(":musical_note:Команды для воспроизведения музыки:musical_note:", localization.getMessage("help.music.text", prefix), false)
                .build();
        event.reply(helpAll);

        if (PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            MessageEmbed helpMod = new EmbedBuilder()
                    .setColor(Color.ORANGE)
                    .addField(":octagonal_sign:Команды для модерирования:octagonal_sign:", localization.getMessage("help.mod.text", prefix), false)
                    .build();
            event.reply(helpMod);
        }
    }


}
