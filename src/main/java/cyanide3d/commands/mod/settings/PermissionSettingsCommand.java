package cyanide3d.commands.mod.settings;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PermissionSettingsCommand extends Command {

    private final Localization localization = Localization.getInstance();
    PermissionService service = PermissionService.getInstance();

    public PermissionSettingsCommand() {
        this.name = "permission";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        final String[] args = event.getArgs().split(" ");
        List<Role> mentionedRoles = event.getMessage().getMentionedRoles();

        if (event.getArgs().isEmpty()) {
            event.reply("Синтаксис команды:" +
                    "\n`$permission [list|add|change|delete] [link] [admin|moderator|user]`");
            return;
        }

        if (args.length > 3 || mentionedRoles.isEmpty() && args.length > 1) {
            event.reply("Ошибка, проверьте синтаксис команды.");
            return;
        }

        String roleId = !mentionedRoles.isEmpty()
                ? mentionedRoles.get(0).getId() : "";

        dispatch(args, roleId, event);
    }

    private void dispatch(String[] args, String roleId, CommandEvent event) {
        try {
            switch (args[0]) {
                case "list":
                    event.reply(getPermRoles(event.getGuild()));
                    break;
                case "add":
                    service.addRole(roleId, Permission.valueOf(args[2].toUpperCase()), event.getGuild().getId());
                    event.reply("Роль успешно наделена полномочиями!");
                    break;
                case "change":
                    service.changeRole(roleId, Permission.valueOf(args[2].toUpperCase()), event.getGuild().getId());
                    event.reply("Полномочия роли успешно изменены!");
                    break;
                case "delete":
                    service.removeRole(roleId, event.getGuild().getId());
                    event.reply("Полномочия c роли успешно сняты!");
                    break;
                default:
                    event.reply("Ошибка в синтаксисе команды.");
            }
        } catch (IllegalArgumentException e) {
            event.reply("Не корректное название привелегии.");
        }
    }

    private MessageEmbed getPermRoles(Guild guild) {

        PermissionService service = PermissionService.getInstance();
        Map<Integer, List<String>> permRoles = service.getPermRoles(guild.getId());

        EmbedBuilder builder = new EmbedBuilder()
                .setThumbnail(guild.getIconUrl())
                .setTitle("Роли и их полномочия.")
                .setFooter("Для добавления: $permission")
                .setColor(Color.ORANGE);

        permRoles.forEach((k, v) ->
                builder.addField(Permission.permByCode(k).name(), getRoleNamesByIDs(v, guild), false));

        return builder.build();
    }

    private String getRoleNamesByIDs(List<String> roleIDs, Guild guild) {
        return roleIDs.stream()
                .map(guild::getRoleById)
                .filter(Objects::nonNull)
                .map(Role::getName)
                .collect(Collectors.joining(", "));
    }
}
