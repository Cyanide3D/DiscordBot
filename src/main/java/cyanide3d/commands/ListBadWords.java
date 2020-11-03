package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.conf.UserAccessToCommand;
import cyanide3d.service.BadWordsService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ListBadWords extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public ListBadWords() {
        this.name = "listword";
        this.aliases = new String[]{"listbadwords"};
        this.arguments = "[word]";
        this.help = localization.getMessage("listword.help");
    }

    @Override
    protected void execute(CommandEvent event) {
//        if(!UserAccessToCommand.getInstance().getAccess(event.getMember(), Permission.MODERATOR)) {
//            event.reply(localization.getMessage("accessDenied",name));
//            return;
//        }
        String list = StringUtils.join(BadWordsService.getInstance().getBadWords(), "\n");
        MessageEmbed message = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(localization.getMessage("listword.list"))
                .addField("", list, false)
                .build();
        event.reply(message);
    }

}