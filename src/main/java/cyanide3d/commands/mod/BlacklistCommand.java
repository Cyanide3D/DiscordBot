package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.BlacklistEntity;
import cyanide3d.service.BlacklistService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class BlacklistCommand extends Command {

    private final Localization localization = Localization.getInstance();
    private List<BlacklistEntity> blacklisted;

    public BlacklistCommand() {
        this.name = "blacklist";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        BlacklistService service = BlacklistService.getInstance();
        blacklisted = service.giveBlacklistedUsers(event.getGuild().getId());

        if (StringUtils.isNotBlank(event.getArgs())) {
            event.reply(checkPerson(service.findOneByUsername(event.getArgs().toLowerCase(), event.getGuild().getId())));
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