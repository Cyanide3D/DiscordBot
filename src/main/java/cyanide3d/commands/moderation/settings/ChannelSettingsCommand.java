package cyanide3d.commands.moderation.settings;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.ChannelService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.ActionType;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

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
        List<TextChannel> mentionedChannels = event.getMessage().getMentionedChannels();

        if (event.getArgs().isEmpty()) {
            event.reply("Синтаксис команды:" +
                    "\n`$channel [list|add|change] [link] [action]`");
            return;
        }

        if (args.length > 3 || mentionedChannels.isEmpty() && args.length > 1) {
            event.reply("Ошибка, проверьте синтаксис команды.");
            return;
        }

        String channelId = mentionedChannels.isEmpty()
                ? "1" : mentionedChannels.get(0).getId();

        dispatch(args, channelId, event);
    }

    private void dispatch(String[] args, String channelId, CommandEvent event) {
        try {
            switch (args[0]) {
                case "list":
                    event.reply(getChannels(event.getGuild()));
                    break;
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

    private MessageEmbed getChannels(Guild guild) {

        ChannelService service = ChannelService.getInstance();

        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setThumbnail(guild.getIconUrl())
                .setFooter("Для добавления: $channel")
                .addField("", service.getChannelsAsString(guild), false)
                .setTitle("СПИСОК КАНАЛОВ.")
                .build();
    }

}
