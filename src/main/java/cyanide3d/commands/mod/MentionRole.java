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
        List<RoleUse> roles = MessageCacheService.getInstance().roleCacheList();
        StringBuilder result = new StringBuilder();
        StringBuilder message = new StringBuilder();
        Set<String> dates = roles.stream().map(RoleUse::getDate).collect(Collectors.toSet());
        for (String date : dates) {
            for (RoleUse role : roles) {
                if (date.equalsIgnoreCase(role.getDate())){
                    Role guildRole = event.getGuild().getRoleById(role.getId());
                    if (guildRole != null)
                        message.append("\n`" + guildRole.getName()).append("` : ").append(role.getCount()).append(" раз(a).");
                }
            }
            result.append("\n**" + date + "**").append(message.toString());
            message.delete(0, message.length()-1);
        }
        event.reply(result.toString());
    }
}
