package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.commands.basic.EmbedTemplates;
import cyanide3d.conf.Config;
import cyanide3d.service.ChannelService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.ActionType;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class Settings extends Command {

    private final Localization localization = Localization.getInstance();
    private final Config config;
    private PermissionService permissionService;
    private ChannelService channelService;

    public Settings() {
        this.name = "settings";
        config = Config.getInstance();
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        permissionService = PermissionService.getInstance();
        channelService = ChannelService.getInstance();

        //if (event.getArgs().length() == 0) event.reply(createMenu());

        String[] args = event.getArgs().split(" ");
        String arg = "";

        if (args.length < 2) {
            event.reply(createMenu());
        } else {
            final String command = args[0].toLowerCase();
            final String subCommand = args[1].toLowerCase();
            if (args.length > 3)
                arg = args[3];
            Role role = null;
            if ("role".equals(command)) {
                List<Role> mentionedRoles = event.getMessage().getMentionedRoles();
                if (!mentionedRoles.isEmpty()) {
                    role = mentionedRoles.get(0);
                }
                roleCommand(event, subCommand, role, arg);
            } else if ("channel".equals(command)) {
                channelCommands(event, subCommand, arg);
            } else {
                event.reply(EmbedTemplates.SYNTAX_ERROR);
            }
        }
    }

    private void roleCommand(CommandEvent event, String subCommand, Role role, String permission) {
        switch (subCommand) {
            case "list":
                event.reply(listRolePermission(event));
                break;
            case "add":
                permissionService.addRole(role, Permission.valueOf(permission), event.getGuild().getId());
                event.reply("Роль успешно наделена полномочиями!");
                break;
            case "change":
                permissionService.changeRole(role, Permission.valueOf(permission), event.getGuild().getId());
                event.reply("Полномочия роли успешно изменены!");
                break;
            case "delete":
                permissionService.removeRole(role, event.getGuild().getId());
                event.reply("Полномочия c роли успешно сняты!");
                break;
        }

    }

    private void channelCommands(CommandEvent event, String subCommand, String channel) {
        String channelID;
        final List<TextChannel> mentionedChannels = event.getMessage().getMentionedChannels();
        switch (subCommand) {
            case "add":
                if (mentionedChannels.size() == 0) {
                    event.reply(EmbedTemplates.SYNTAX_ERROR);
                    return;
                }
                channelID = mentionedChannels.get(0).getId();
                channelService.addChannel(channelID, ActionType.valueOf(channel), event.getGuild().getId());
                event.reply("Канал успешно добавлен!");
                break;
            case "delete":
                channelService.deleteChannel(ActionType.valueOf(channel), event.getGuild().getId());
                event.reply("Канал для действия успешно удалён!");
                break;
            case "change":
                if (mentionedChannels.size() == 0) {
                    event.reply(EmbedTemplates.SYNTAX_ERROR);
                    return;
                }
                channelID = mentionedChannels.get(0).getId();
                channelService.changeChannel(channelID, ActionType.valueOf(channel), event.getGuild().getId());
                event.reply("Канал для действия успешно изменён!");
        }
    }

    @NotNull
    private MessageEmbed createMenu() {
        return new EmbedBuilder()
                .setTitle(localization.getMessage("settings.title"))
                .setColor(Color.ORANGE)
                .addField(localization.getMessage("settings.title.role"), localization.getMessage("settings.op.role", config.getPrefix()), true)
                .addField("", "", false)
                .addField(localization.getMessage("settings.title.channel"), localization.getMessage("settings.op.channel", config.getPrefix()), false)
                .build();
    }

    private MessageEmbed listRolePermission(CommandEvent e) {
        StringBuilder owner = new StringBuilder();
        StringBuilder stmod = new StringBuilder();
        StringBuilder mod = new StringBuilder();
        final Guild guild = e.getGuild();
        final List<Member> owners = getMembersWithPermission(guild, Permission.OWNER);
        final List<Member> admins = getMembersWithPermission(guild, Permission.ADMIN);
        final List<Member> moderators = getMembersWithPermission(guild, Permission.MODERATOR);
        admins.removeAll(owners);
        moderators.removeAll(owners);
        moderators.removeAll(admins);

        owners.forEach(member -> addUser(owner, member));
        admins.forEach(member -> addUser(stmod, member));
        moderators.forEach(member -> addUser(mod, member));
        return EmbedTemplates.listUsersWithRoles(owner.toString(), stmod.toString(), mod.toString());
    }

    private List<Member> getMembersWithPermission(Guild guild, Permission permission) {
//        List<String> rolesWithPermission = permissionService.getRoleIdsByPermission(permission);
//        if (rolesWithPermission.size() == 0) {
//            return new ArrayList<>();
//        }
//        return guild.getMembersWithRoles(rolesWithPermission
//                .stream()
//                .map(guild::getRoleById)
//                .collect(Collectors.toList()));
        return Collections.emptyList();
    }

    private void addUser(StringBuilder owner, Member member) {
        owner.append(member.getNickname())
                .append(" | ")
                .append(member.getUser().getName())
                .append("\n");
    }
}
