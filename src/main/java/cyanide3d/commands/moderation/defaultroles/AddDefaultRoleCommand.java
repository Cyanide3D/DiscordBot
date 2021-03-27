package cyanide3d.commands.moderation.defaultroles;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.DefaultRoleService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.stream.Collectors;

public class AddDefaultRoleCommand extends Command {

    private final Localization localization = Localization.getInstance();
    private final DefaultRoleService service;

    public AddDefaultRoleCommand() {
        service = DefaultRoleService.getInstance();
        this.name = "adddefaultrole";
        this.aliases = new String[]{"adr"};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        final List<String> mentionedRoles = event.getMessage().getMentionedRoles()
                .stream().map(Role::getId)
                .collect(Collectors.toList());

        if (mentionedRoles.isEmpty()) {
            event.reply("Необходимо линкануть хотя бы одну роль.");
            return;
        }

        service.addRole(mentionedRoles, event.getGuild().getId());
        event.reply("Роль успешно добавлена.");

    }
}
