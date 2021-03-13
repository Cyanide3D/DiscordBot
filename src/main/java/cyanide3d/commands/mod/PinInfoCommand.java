package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import cyanide3d.service.PermissionService;
import cyanide3d.service.Giveaway;
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
        if (!new PermissionService(PermissionEntity.class, event.getGuild().getId()).checkPermission(event.getMember(), Permission.MODERATOR)) {
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
                .setFooter("From Defiant'S with love :)")
                .addField("Оставшиеся пины:", giveaway.getPinList(), false)
                .addField("Пины забрали:", giveaway.getReactedUserList(), false)
                .build();
    }
}
