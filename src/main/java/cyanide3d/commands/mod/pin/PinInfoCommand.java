package cyanide3d.commands.mod.pin;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.Giveaway;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class PinInfoCommand extends Command {
    private final Localization localization = Localization.getInstance();
    private final Giveaway giveaway = Giveaway.getInstance();

    public PinInfoCommand() {
        this.name = "pininfo";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        synchronized (giveaway) {
            if (giveaway.isEmpty()) {
                event.reply("Раздача пинов окончена или еще не была начата.");
                return;
            }
            event.reply(createMessage(event));
        }
    }

    @NotNull
    private MessageEmbed createMessage(CommandEvent event) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .addField("Оставшиеся пины:", giveaway.getPinList(event.getGuild().getId()), false)
                .addField("Пины забрали:", giveaway.getReactedUserList(event.getGuild().getId()), false)
                .build();
    }
}
