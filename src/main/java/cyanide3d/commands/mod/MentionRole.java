package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.model.RoleUse;
import cyanide3d.service.MessageCacheService;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MentionRole extends Command {
    private final Localization localization = Localization.getInstance();

    public MentionRole() {
        this.name = "mention";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        if (event.getArgs().isEmpty()) {
            event.reply(
                    "Необходимо указать дату.\n\n" +
                    "Пример: `!mention dd:mm:yyyy`"
            );
            return;
        }

        StringBuilder builder = new StringBuilder();
        List<RoleUse> roles = MessageCacheService.getInstance().roleCacheList()
                .stream()
                .filter(roleUse -> roleUse.getDate().equals(event.getArgs()))
                .collect(Collectors.toList());

        if (roles.isEmpty()) {
            event.reply("Нет упоминаний.");
            return;
        }

        for (RoleUse roleUse : roles) {
            Role role = event.getGuild().getRoleById(roleUse.getId());

            if (role == null)
                continue;

            builder
                    .append(role.getName())
                    .append(" : ")
                    .append(roleUse.getCount())
                    .append(" раз(a).")
                    .append("\n");
        }

        event.reply(builder.toString());
    }
}
