package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.MessageCacheService;
import cyanide3d.service.PermissionService;
import cyanide3d.service.PinService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
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
        if (pinService.getPins().isEmpty()){
            event.reply("Раздача пинов окончена или еще не была начата.");
            return;
        }
        event.reply(getEmbedTemplate(event));
    }

    @NotNull
    private MessageEmbed getEmbedTemplate(CommandEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setThumbnail(event.getGuild().getIconUrl())
                .setFooter("From Defiant'S with love :)");
        StringBuilder field = new StringBuilder();

        for (String pin : pinService.getPins()) {
            field.append(pin).append("\n");
        }
        embed.addField("Оставшиеся пины:", field.toString(), false);
        field.delete(0,field.length()-1);
        for (Member user : pinService.getReactedUser()) {
            String name = user.getNickname() == null ? user.getUser().getName() : user.getNickname();
            field.append(name).append("\n");
        }
        embed.addField("Пины забрали:", field.toString(), false);
        return embed.build();
    }
}
