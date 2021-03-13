package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import cyanide3d.service.PermissionService;
import cyanide3d.service.Giveaway;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

public class PinCommand extends Command {
    private final Localization localization = Localization.getInstance();
    private final Giveaway giveaway = Giveaway.getInstance();

    public PinCommand() {
        this.name = "pin";
    }

    @Override
    protected void execute(CommandEvent event) {

        event.getMessage().delete().queue();

        if (!new PermissionService(PermissionEntity.class, event.getGuild().getId()).checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().isEmpty()) {
            event.reply("**Нужны пины для работы.**");
            return;
        }


        giveaway.start(StringUtils.split(event.getArgs(), '\n'));

        Role role = event.getGuild().getRoleById("664863242199236629");
        if (role != null) {
            event.reply(role.getAsMention() + "\n");
        }

        Message message = event.getTextChannel().sendMessage(createMessage(event.getAuthor())).complete();
        message.addReaction("\uD83E\uDD21").queue();

        giveaway.setMessage(message);
    }

    private MessageEmbed createMessage(User user) {
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
