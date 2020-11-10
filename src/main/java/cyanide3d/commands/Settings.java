package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Config;
import cyanide3d.conf.Permission;
import cyanide3d.exceprtion.UnsupportedActionException;
import cyanide3d.exceprtion.UnsupportedPermissionException;
import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;

public class Settings extends Command {

    final PermissionService permissionService = PermissionService.getInstance();
    final ChannelManagmentService channelManagmentService = ChannelManagmentService.getInstance();
    private final Localization localization = Localization.getInstance();
    private final Config config;

    public Settings() {
        this.name = "settings";
        this.aliases = new String[]{"changeperm"};
        this.arguments = "[subcommand] [action]";
        this.help = "Настройки полномочий. (Только для уполномоченых лиц)";
        config = Config.getInstance();
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        MessageEmbed settingsMenu = new EmbedBuilder()
                .setTitle(localization.getMessage("settings.title"))
                .setColor(Color.ORANGE)
                .addField(localization.getMessage("settings.title.role"), localization.getMessage("settings.op.role", config.getPrefix()), true)
                .addField("", "", false)
                .addField(localization.getMessage("settings.title.channel"), localization.getMessage("settings.op.channel", config.getPrefix()), false)
                .build();

        String[] args = event.getArgs().split(" ");
        if (event.getArgs().length() == 0) event.reply("settingsMenu");
        if (args.length >= 2 && args[0].equalsIgnoreCase("role")) {
            try {
                Role mentionRole;
                switch (args[1]) {
                    case "list":
                        new ListRolePermissions().listRolePermission(event);
                        break;
                    case "add":
                        mentionRole = event.getMessage().getMentionedRoles().get(0);
                        permissionService.addRole(mentionRole, args[3]);
                        event.reply("Роль успешно наделена полномочиями!");
                        break;
                    case "change":
                        mentionRole = event.getMessage().getMentionedRoles().get(0);
                        permissionService.changeRole(mentionRole, args[3]);
                        event.reply("Полномочия роли успешно изменены!");
                        break;
                    case "delete":
                        mentionRole = event.getMessage().getMentionedRoles().get(0);
                        permissionService.removeRole(mentionRole, args[3]);
                        event.reply("Полномочия c роли успешно сняты!");
                        break;
                }
            } catch (UnsupportedPermissionException | IndexOutOfBoundsException e) {
                event.reply(EmbedTemplates.SYNTAX_ERROR);
            }
        }
        if (args.length >= 2 && args[0].equalsIgnoreCase("channel")) {
            try {
                String channelID;
                switch (args[1]){
                    case "add":
                        channelID = event.getMessage().getMentionedChannels().get(0).getId();
                        channelManagmentService.addChannel(channelID, args[args.length - 1]);
                        event.reply("Канал успешно добавлен!");
                        break;
                    case "delete":
                        channelManagmentService.deleteChannel(args[args.length - 1]);
                        event.reply("Канал для действия успешно удалён!");
                        break;
                    case "change":
                        channelID = event.getMessage().getMentionedChannels().get(0).getId();
                        channelManagmentService.changeChannel(channelID, args[args.length - 1]);
                        event.reply("Канал для действия успешно изменён!");
                        break;
                }
            } catch (IndexOutOfBoundsException e) {
                event.reply(EmbedTemplates.SYNTAX_ERROR);
            } catch (UnsupportedActionException e){
                event.reply(e.getMessage());
            }
        }
    }


}
