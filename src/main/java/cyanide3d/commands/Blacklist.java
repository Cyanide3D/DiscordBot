package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.BlackListService;
import cyanide3d.service.EnableActionService;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Map;

public class Blacklist extends Command {

    private final Localization localization = Localization.getInstance();
    final BlackListService blackListService = BlackListService.getInstance();
    final Map<String, String> blacklistedUsers = blackListService.giveBlacklistedUsers();

    public Blacklist() {
        this.name = "blacklist";
        this.arguments = "[user]";
        this.help = "Просмотр чёрного списка. (Только для уполномоченых лиц)";
    }

    @Override
    protected void execute(CommandEvent event) {
        EnableActionService enableActionService = EnableActionService.getInstance();
        if (!enableActionService.getState("blacklist")){
            return;
        }
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs() != "") {
            if (blacklistedUsers.containsKey(event.getArgs().toLowerCase())) {
                event.reply("**Пользователь найден в чёрном списке!**\nДобавлен по причине: " + blacklistedUsers.get(event.getArgs().toLowerCase()));
            } else event.reply("Пользователя нет в чёрном списке.");
            return;
        }
        StringBuilder usernames = new StringBuilder();
        blacklistedUsers.forEach((username, reason) -> usernames.append(username).append(" : ").append(reason).append("\n"));

        MessageEmbed message = new EmbedBuilder()
                .addField("Чёрный список:", usernames.toString(), false)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter("From Defiant'S with love:)")
                .build();
        event.reply(message);
    }
}