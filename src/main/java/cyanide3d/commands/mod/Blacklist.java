package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import cyanide3d.service.BlacklistService;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class Blacklist extends Command {

    private final Localization localization = Localization.getInstance();

    public Blacklist() {
        this.name = "blacklist";
    }

    @Override
    protected void execute(CommandEvent event) {
//        if (!new PermissionService(PermissionEntity.class, event.getGuild().getId()).checkPermission(event.getMember(), Permission.MODERATOR)) {
//            event.reply(localization.getMessage("accessDenied", name));
//            return;
//        }
//
//        if (StringUtils.isNotBlank(event.getArgs())) {
//            event.reply(checkPerson(event.getArgs().toLowerCase()));
//        } else {
//            event.reply(printList(event.getGuild().getIconUrl()));
//        }
//    }
//
//    private MessageEmbed printList(String iconUrl) {
//        StringBuilder usernames = new StringBuilder();
//        blacklistedUsers.forEach((username, reason) -> usernames.append('`').append(username).append("` : ").append(reason).append("\n"));
//        return new EmbedBuilder()
//                .addField("Чёрный список:", usernames.toString(), false)
//                .setThumbnail(iconUrl)
//                .setFooter("From Defiant'S with love:)")
//                .build();
//    }
//
//    private String checkPerson(String username) {
//        return blacklistedUsers.containsKey(username)
//                ? "**Пользователь найден в чёрном списке!**\nДобавлен по причине: " + blacklistedUsers.get(username)
//                : "Пользователя нет в чёрном списке.";
    }
}