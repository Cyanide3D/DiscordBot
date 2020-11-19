package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.BadWordsService;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

public class ListBadWords extends Command {

    private final Localization localization = Localization.getInstance();

    public ListBadWords() {
        this.name = "listword";
        this.aliases = new String[]{"listbadwords"};
        this.arguments = "[word]";
        this.help = localization.getMessage("listword.help");
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        String list = StringUtils.join(BadWordsService.getInstance().getBadWords(), ", ");
        event.reply(localization.getMessage("listword.list") + "\n" + list);
    }

}