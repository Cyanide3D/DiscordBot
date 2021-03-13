package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class Help extends Command {

    private final Localization localization = Localization.getInstance();

    public Help(){
        this.name = "help";
    }

    @Override
    protected void execute(CommandEvent event) {
        CommandClientManager commandClientManager = CommandClientManager.getInstance();
        MessageEmbed helpAll = new EmbedBuilder()
                .setTitle("Доступные команды бота:")
                .setColor(Color.ORANGE)
                .addField(":bulb:Основные команды:bulb:", localization.getMessage("help.all", commandClientManager.getPrefix()), false)
                .addField(":game_die:Фановые команды:game_die:", localization.getMessage("help.fun", commandClientManager.getPrefix()), false)
                .addField(":musical_note:Команды для воспроизведения музыки:musical_note:", localization.getMessage("help.music", commandClientManager.getPrefix()), false)
                .build();
        MessageEmbed helpMod = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .addField(":octagonal_sign:Команды для модерирования:octagonal_sign:", localization.getMessage("help.mod", commandClientManager.getPrefix()), false)
                .build();
        event.reply(helpAll);
        if(new PermissionService(PermissionEntity.class, event.getGuild().getId()).checkPermission(event.getMember(), Permission.MODERATOR)){
            event.reply(helpMod);
        }
    }
}
