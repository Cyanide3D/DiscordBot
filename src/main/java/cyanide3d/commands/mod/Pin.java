package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.PermissionService;
import cyanide3d.service.PinService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.internal.entities.EmoteImpl;
import net.dv8tion.jda.internal.requests.Route;

import java.awt.*;

public class Pin extends Command {
    private final Localization localization = Localization.getInstance();

    public Pin() {
        this.name = "pin";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        PinService pinService = PinService.getInstance();
        if (event.getArgs().isEmpty()) {
            event.reply("**Нужны пины для работы.**");
            return;
        }
        pinService.getReactedUser().clear();
        if (pinService.getParseMessage() != null)
            pinService.getParseMessage().delete().queue();
        pinService.setPins(event.getArgs().split("\n"));
        event.getMessage().delete().queue();
        Role role = event.getGuild().getRoleById("664863242199236629");
        MessageEmbed message = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setTitle("РАЗДАЧА ПИНОВ.")
                .setDescription(":arrow_right:  **От: **" + event.getAuthor().getAsMention() + "  :arrow_left:")
                .addField("Нажмите на эмодзи под сообщением чтобы получить пин!", ":grey_exclamation: Пин можно получить только 1 раз за раздачу!", false)
                .addField("Пин придёт в личные сообщения.", ":grey_exclamation: Сообщение автоматически удалится когда пины кончаться!", false)
                .setFooter("From Defiant'S with love :)")
                .setThumbnail(event.getAuthor().getEffectiveAvatarUrl())
                .build();
        if (role != null)
            event.reply(role.getAsMention() + "\n");
        Message msg = event.getTextChannel().sendMessage(message).complete();
        pinService.setParseMessage(msg);
        msg.addReaction("\uD83E\uDD21").queue();
    }
}
