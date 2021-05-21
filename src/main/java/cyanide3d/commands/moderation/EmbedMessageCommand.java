package cyanide3d.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Random;

import static java.awt.Color.*;

public class EmbedMessageCommand extends Command {

    private final Localization localization;
    private final Color[] colors = {
            ORANGE, BLACK, GREEN, RED, BLUE, CYAN, YELLOW, MAGENTA, WHITE
    };

    public EmbedMessageCommand() {
        localization = Localization.getInstance();
        this.name = "emsg";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            String[] args = event.getArgs().split("\n");
            event.getMessage().delete().queue();
            try {
                event.reply(getMessage(args));
            } catch (IndexOutOfBoundsException e) {
                event.reply(localization.getMessage("command.incorrect.syntax"));
            } catch (Exception e) {
                event.reply(localization.getMessage("command.error"));
            }
        } else {
            event.reply(localization.getMessage("accessDenied", name));
        }
    }

    private MessageEmbed getMessage(String[] args) {
        return new EmbedBuilder()
                .setTitle(getTitle(args[0]))
                .setDescription(args[1])
                .setImage(getImage(args[2]))
                .setColor(getColor())
                .build();
    }

    private String getTitle(String title) {
        return title.equals("-") ? null : title;
    }

    private Color getColor() {
        return colors[new Random().nextInt(colors.length)];
    }

    private String getImage(String image) {
        return image.equals("-") ? null : image;
    }

}
