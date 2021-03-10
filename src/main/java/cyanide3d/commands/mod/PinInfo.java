package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.PermissionService;
import cyanide3d.service.PinService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class PinInfo extends Command {
    private final Localization localization = Localization.getInstance();
    private final PinService pinService = PinService.getInstance();

    public PinInfo() {
        this.name = "pininfo";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (pinService.isEmptyPinPool()) {
            event.reply("Раздача пинов окончена или еще не была начата.");
            return;
        }
        event.reply(getEmbedTemplate(event));
    }

    @NotNull
    private MessageEmbed getEmbedTemplate(CommandEvent event) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter("From Defiant'S with love :)")
                .addField("Оставшиеся пины:", pinService.getPinList(), false)
                .addField("Пины забрали:", pinService.getReactedUserList(), false)
                .build();
    }
}
