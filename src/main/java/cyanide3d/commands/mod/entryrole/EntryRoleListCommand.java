package cyanide3d.commands.mod.entryrole;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.EntryRoleService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.stream.Collectors;

public class EntryRoleListCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public EntryRoleListCommand() {
        this.name = "listentryrole";
        this.aliases = new String[]{"ler"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        StringBuilder builder = new StringBuilder()
                .append("***СПИСОК РОЛЕЙ:***\n-------------------------------\n");

        EntryRoleService service = EntryRoleService.getInstance();
        final List<Role> roles = service.getAllRoleIDs(event.getGuild().getId())
                .stream().map(id -> event.getGuild().getRoleById(id))
                .collect(Collectors.toList());

        event.reply(getRoleList(roles, builder));

    }

    private String getRoleList(List<Role> roles, StringBuilder builder) {
        if (roles.isEmpty())
            return "Список пуст";

        for (Role role : roles) {
            builder
                    .append("**")
                    .append(role.getName())
                    .append("** ");
        }

        return builder.toString();
    }

}
