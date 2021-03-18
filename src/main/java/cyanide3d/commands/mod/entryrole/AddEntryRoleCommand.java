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

public class AddEntryRoleCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public AddEntryRoleCommand() {
        this.name = "addentryrole";
        this.aliases = new String[]{"aer"};
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

        EntryRoleService service = EntryRoleService.getInstance();
        service.add(mentionedRoles, event.getGuild().getId());
        event.reply("Роль успешно добавлена.");

    }
}
