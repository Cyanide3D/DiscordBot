package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.BlackListService;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class Blacklist extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public Blacklist() {
        this.name = "blacklist";
        this.help = "Просмотр чёрного списка.";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        String usernames="";
        Map<String,String> user = BlackListService.getInstance().giveBlacklistedUsers();
        for(String username : user.keySet()){
            usernames += (username+" : " + user.get(username) + "\n");
        }
        MessageEmbed message = new EmbedBuilder()
                .addField("Чёрный список:",usernames,false)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter("From Defiant'S with love:)")
                .build();
        event.reply(message);
    }
}
