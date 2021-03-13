package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.BlacklistEntity;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import cyanide3d.service.BlacklistService;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class Blacklist extends Command {

    private final Localization localization = Localization.getInstance();
    private List<BlacklistEntity> blacklisted;

    public Blacklist() {
        this.name = "blacklist";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!new PermissionService(PermissionEntity.class, event.getGuild().getId()).checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        BlacklistService service = new BlacklistService(BlacklistEntity.class, event.getGuild().getId());
        blacklisted = service.giveBlacklistedUsers();

        if (StringUtils.isNotBlank(event.getArgs())) {
            event.reply(checkPerson(service.findOneByUsername(event.getArgs().toLowerCase())));
        } else {
            event.reply(printList(event.getGuild().getIconUrl()));
        }
    }

    private MessageEmbed printList(String iconUrl) {
        StringBuilder usernames = new StringBuilder();
        blacklisted.forEach(user -> usernames.append('`').append(user.getName()).append("` : ").append(user.getReason()).append("\n"));
        return new EmbedBuilder()
                .addField("Чёрный список:", usernames.toString(), false)
                .setThumbnail(iconUrl)
                .setFooter("From Defiant'S with love:)")
                .build();
    }

    private String checkPerson(BlacklistEntity entity) {
        return entity != null
                ? "**Пользователь найден в чёрном списке!**\nДобавлен по причине: " + entity.getReason()
                : "Пользователя нет в чёрном списке.";
    }
}