package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.commands.basic.EmbedTemplates;
import cyanide3d.conf.Config;
import cyanide3d.conf.Permission;
import cyanide3d.exceprtion.UnsupportedActionException;
import cyanide3d.exceprtion.UnsupportedPermissionException;
import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Settings extends Command {

    final PermissionService permissionService = PermissionService.getInstance();
    final ChannelManagmentService channelManagmentService = ChannelManagmentService.getInstance();
    private final Localization localization = Localization.getInstance();
    private final Config config;

    public Settings() {
        this.name = "settings";
        config = Config.getInstance();
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        //if (event.getArgs().length() == 0) event.reply(createMenu());

        String[] args = event.getArgs().split(" ");

        if (args.length < 2){
            event.reply(createMenu());
        } else {
            final String command = args[0].toLowerCase();
            final String subCommand = args[1].toLowerCase();
            if ("role".equals(command)) {
                final Role role = event.getMessage().getMentionedRoles().get(0);
                roleCommand(event, subCommand, role, args[3]);
            } else if ("channel".equals(command)) {
                channelCommands(event, subCommand, args[3]);
            } else {
                event.reply(EmbedTemplates.SYNTAX_ERROR);
            }
        }
    }

    private void roleCommand(CommandEvent event, String subCommand, Role role, String permission) {
        try {
            switch (subCommand) {
                case "list":
                    event.reply(listRolePermission(event));
                    break;
                case "add":
                    permissionService.addRole(role, permission);
                    event.reply("Роль успешно наделена полномочиями!");
                    break;
                case "change":
                    permissionService.changeRole(role, permission);
                    event.reply("Полномочия роли успешно изменены!");
                    break;
                case "delete":
                    permissionService.removeRole(role, permission);
                    event.reply("Полномочия c роли успешно сняты!");
                    break;
            }
        } catch (UnsupportedPermissionException e) {
            event.reply(EmbedTemplates.SYNTAX_ERROR);
        }
    }

    private void channelCommands(CommandEvent event, String subCommand, String channel) {
        try {
            String channelID;
            final List<TextChannel> mentionedChannels = event.getMessage().getMentionedChannels();
            switch (subCommand) {
                case "add":
                    if (mentionedChannels.size() == 0) {
                        event.reply(EmbedTemplates.SYNTAX_ERROR);
                        return;
                    }
                    channelID = mentionedChannels.get(0).getId();
                    channelManagmentService.addChannel(channelID, channel);
                    event.reply("Канал успешно добавлен!");
                    break;
                case "delete":
                    channelManagmentService.deleteChannel(channel);
                    event.reply("Канал для действия успешно удалён!");
                    break;
                case "change":
                    if (mentionedChannels.size() == 0) {
                        event.reply(EmbedTemplates.SYNTAX_ERROR);
                        return;
                    }
                    channelID = mentionedChannels.get(0).getId();
                    channelManagmentService.changeChannel(channelID, channel);
                    event.reply("Канал для действия успешно изменён!");
            }
        } catch (UnsupportedActionException e) {
            event.reply(e.getMessage());
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
        java.util.List<Member> users = guild.getMembers();
        final Map<String, Permission> permissions = permissionService.getPermissions();
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
        return guild.getMembersWithRoles(permissionService.getRoleIdsByPermission(permission)
                .stream()
                .map(guild::getRoleById)
                .collect(Collectors.toList()));
    }

    private void addUser(StringBuilder owner, Member member) {
        owner.append(member.getNickname())
                .append(" | ")
                .append(member.getUser().getName())
                .append("\n");
    }
}
