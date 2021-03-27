package cyanide3d.commands.moderation.settings;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.PermissionService;
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
    private final int MAX_ARGS_SIZE = 3;
    private final int MIN_ARGS_SIZE = 1;
    private final String DEFAULT_ID = "1";
    private final int PERMISSION_INDEX = 2;
    private final int SUBCOMMAND_INDEX = 0;
    private final String ARGS_SEPARATOR = " ";

    public PermissionSettingsCommand() {
        this.name = "permission";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        final String[] args = event.getArgs().split(ARGS_SEPARATOR);
        List<Role> mentionedRoles = event.getMessage().getMentionedRoles();

        if (event.getArgs().isEmpty()) {
            event.reply("Синтаксис команды:" +
                    "\n`$permission [list|add|change|delete] [link] [admin|moderator|user]`");
            return;
        }

        if (args.length > MAX_ARGS_SIZE || mentionedRoles.isEmpty() && args.length > MIN_ARGS_SIZE) {
            event.reply("Ошибка, проверьте синтаксис команды.");
            return;
        }

        String roleId = !mentionedRoles.isEmpty()
                ? mentionedRoles.get(0).getId() : DEFAULT_ID;

        dispatch(args, roleId, event);
    }

    private void dispatch(String[] args, String roleId, CommandEvent event) {
        try {
            switch (args[SUBCOMMAND_INDEX]) {
                case "list":
                    event.reply(getPermRoles(event.getGuild()));
                    break;
                case "add":
                    service.empowerRole(roleId, Permission.valueOf(args[PERMISSION_INDEX].toUpperCase()), event.getGuild().getId());
                    event.reply("Роль успешно наделена полномочиями!");
                    break;
                case "change":
                    service.changePermAsRole(roleId, Permission.valueOf(args[PERMISSION_INDEX].toUpperCase()), event.getGuild().getId());
                    event.reply("Полномочия роли успешно изменены!");
                    break;
                case "delete":
                    service.removePermAsRole(roleId, event.getGuild().getId());
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
        Map<Integer, List<String>> permRoles = service.getRoleIdWithPerms(guild.getId());

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
