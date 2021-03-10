package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.PermissionService;
import cyanide3d.service.PinService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;

public class Pin extends Command {
    private final Localization localization = Localization.getInstance();
    private final PinService pinService = PinService.getInstance();

    public Pin() {
        this.name = "pin";
    }

    @Override
    protected void execute(CommandEvent event) {

        event.getMessage().delete().queue();

        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().isEmpty()) {
            event.reply("**Нужны пины для работы.**");
            return;
        }

        pinService.init(event.getArgs().split("\n"));

        send(event);
    }

    private void send(CommandEvent event) {
        Role role = event.getGuild().getRoleById("664863242199236629");
        if (role != null)
            event.reply(role.getAsMention() + "\n");

        Message message = event.getTextChannel().sendMessage(getMessage(event.getAuthor())).complete();
        message.addReaction("\uD83E\uDD21").queue();

        pinService.setParseMessage(message);
    }

    private MessageEmbed getMessage(User user) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setTitle("РАЗДАЧА ПИНОВ.")
                .setDescription(":arrow_right:  **От: **" + user.getAsMention() + "  :arrow_left:")
                .addField("Нажмите на эмодзи под сообщением чтобы получить пин!", ":grey_exclamation: Пин можно получить только 1 раз за раздачу!", false)
                .addField("Пин придёт в личные сообщения.", ":grey_exclamation: Сообщение автоматически удалится когда пины кончатся!", false)
                .setFooter("From Defiant'S with love :)")
                .setThumbnail(user.getEffectiveAvatarUrl())
                .build();
    }

}
