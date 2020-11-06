package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.BlackListService;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import java.util.Locale;
import java.util.Map;

public class Blacklist extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public Blacklist() {
        this.name = "blacklist";
        this.arguments = "[user]";
        this.help = "Просмотр чёрного списка. (Только для уполномоченых лиц)";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        BlackListService blackListService = BlackListService.getInstance();
        if(event.getArgs()!=""){
            if (blackListService.giveBlacklistedUsers().containsKey(event.getArgs())){
                event.reply("**Пользователь найден в чёрном списке!**\nДобавлен по причине: " + blackListService.giveBlacklistedUsers().get(event.getArgs()));
            } else event.reply("Пользователя нет в чёрном списке.");
            return;
        }
        String usernames="";
        Map<String,String> user = blackListService.giveBlacklistedUsers();
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
