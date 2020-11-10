package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.listener.CommandListener;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Locale;

public class Help extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public Help(){
        this.name = "help";
    }

    @Override
    protected void execute(CommandEvent event) {
        CommandListener commandListener = CommandListener.getInstance();
        MessageEmbed helpAll = new EmbedBuilder()
                .setTitle("Доступные команды бота:")
                .setColor(Color.ORANGE)
                .addField(":bulb:Основные команды:bulb:", localization.getMessage("help.all", commandListener.getPrefix()), false)
                .addField(":game_die:Фановые команды:game_die:", localization.getMessage("help.fun", commandListener.getPrefix()), false)
                .build();
        MessageEmbed helpMod = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .addField(":octagonal_sign:Команды для модерирования:octagonal_sign:", localization.getMessage("help.mod", commandListener.getPrefix()), false)
                .build();
        event.reply(helpAll);
        if(PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)){
            event.reply(helpMod);
        }
    }
}
