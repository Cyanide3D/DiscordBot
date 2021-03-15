package cyanide3d.commands.mod.settings;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.ChannelService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.ActionType;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

public class ChannelSettingsCommand extends Command {

    private final Localization localization = Localization.getInstance();
    ChannelService service = ChannelService.getInstance();

    public ChannelSettingsCommand() {
        this.name = "channel";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        final String[] args = event.getArgs().split(" ");
        final TextChannel textChannel = event.getMessage().getMentionedChannels().get(0);

        if (args.length < 2 || args.length > 3 || textChannel == null) {
            event.reply("Ошибка, проверьте синтаксис команды.");
            return;
        }

        dispatch(args, textChannel.getId(), event);
    }

    private void dispatch(String[] args, String channelId, CommandEvent event) {
        try {
            switch (args[0]) {
                case "add":
                    service.addChannel(channelId, ActionType.valueOf(args[2].toUpperCase()), event.getGuild().getId());
                    event.reply("Канал успешно добавлен!");
                    break;
                case "change":
                    service.changeChannel(channelId, ActionType.valueOf(args[2].toUpperCase()), event.getGuild().getId());
                    event.reply("Канал для действия успешно удалён!");
                    break;
            }
        } catch (IllegalArgumentException e) {
            event.reply("Не корректное название функции.");
        }
    }
}
