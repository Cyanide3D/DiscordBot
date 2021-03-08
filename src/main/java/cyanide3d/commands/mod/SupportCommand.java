package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Logging;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class SupportCommand extends Command {

    public SupportCommand() {
        this.name = "sc";
    }


    @Override
    protected void execute(CommandEvent event) {
        if (!event.getAuthor().getId().equals("534894366448156682")) {
            return;
        }
        final Role pve = event.getGuild().getRolesByName("PVE", true).get(0);
        final Role pvp = event.getGuild().getRolesByName("PVP", true).get(0);
        event.getChannel().sendMessage(new EmbedBuilder()
                .setTitle("Режимы")
                .setColor(Color.ORANGE)
                .setDescription(
                        "Выберите предпочитаемый " +
                                "\nвами игровой режим." +
                                "\n" +
                                "\n:crossed_swords: - " + pvp.getAsMention() +
                                "\n:shield: - " + pve.getAsMention()
                )
                .setFooter("From Defiant'S with love :)")
                .build()).queue();
    }
}
