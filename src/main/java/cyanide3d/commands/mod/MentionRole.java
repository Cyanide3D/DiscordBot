package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.RoleEntity;
import cyanide3d.service.PermissionService;
import cyanide3d.service.RoleService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.stream.Collectors;

public class MentionRole extends Command {
    private final Localization localization = Localization.getInstance();

    public MentionRole() {
        this.name = "mention";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
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

        RoleService service = RoleService.getInstance();

        StringBuilder builder = new StringBuilder();
        List<RoleEntity> roles = service.listByGuildId(event.getGuild().getId())
                .stream()
                .filter(role -> role.getDate().equals(event.getArgs()))
                .collect(Collectors.toList());

        if (roles.isEmpty()) {
            event.reply("Нет упоминаний.");
            return;
        }

        for (RoleEntity entity : roles) {
            Role role = event.getGuild().getRoleById(entity.getId());

            if (role == null)
                continue;

            builder
                    .append(role.getName())
                    .append(" : ")
                    .append(entity.getCount())
                    .append(" раз(a).")
                    .append("\n");
        }

        event.reply(builder.toString());
    }
}
