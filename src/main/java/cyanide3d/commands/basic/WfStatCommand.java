package cyanide3d.commands.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.model.json.WarfacePlayerStats;
import cyanide3d.util.DefaultAlertMessages;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WfStatCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public WfStatCommand() {
        this.name = "wfstat";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs() == null) {
            event.reply(localization.getMessage("command.wfstat.no.args"));
            return;
        }
        try {
            MessageEmbed message = getMessageWithStats(event);
            event.reply(message);
        } catch (IOException e) {
            errorHandle(event);
        }
    }

    @NotNull
    private MessageEmbed getMessageWithStats(CommandEvent event) throws IOException {
        String nickname = event.getArgs();
        WarfacePlayerStats playerInfo = new ObjectMapper().readValue(new URL("http://api.warface.ru/user/stat/?name=" + URLEncoder.encode(nickname, StandardCharsets.UTF_8) + "&server=3"), WarfacePlayerStats.class);
        String[] fullStats = playerInfo.getFullResponse().split("\n");

        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setImage(DefaultAlertMessages.getWfStatImage())
                .setThumbnail(event.getGuild().getIconUrl())
                .addField(localization.getMessage("command.wfstat.field.stats.title", playerInfo.getNickname()), localization.getMessage("command.wfstat.field.stats.text", playerInfo.getRankId(), playerInfo.getPlaytimeH(), playerInfo.getClanName(), StringUtils.substringAfter(fullStats[3], "="), StringUtils.substringAfter(fullStats[1], "="), StringUtils.substringAfter(fullStats[2], "="), StringUtils.substringAfter(fullStats[0], "="), StringUtils.substringAfter(fullStats[46], "="), StringUtils.substringAfter(fullStats[45], "="), StringUtils.substringAfter(fullStats[14], "=")), false)
                .addField(localization.getMessage("command.wfstat.field.stats.pve.title"), localization.getMessage("command.wfstat.field.stats.pve.text", playerInfo.getPve(), playerInfo.getPveAll(), playerInfo.getPveWins(), playerInfo.getPveLost(), playerInfo.getPveKill(), playerInfo.getPveDeath(), playerInfo.getFavoritPVE(), playerInfo.getPveFriendlyKills()), true)
                .addField(localization.getMessage("command.wfstat.field.stats.pvp.title"), localization.getMessage("command.wfstat.field.stats.pvp.text", playerInfo.getPvp(), playerInfo.getPvpAll(), playerInfo.getPvpWins(), playerInfo.getPvpLost(), playerInfo.getKills(), playerInfo.getDeath(), playerInfo.getFavoritPVP()), true)
                .build();
    }

    private void errorHandle(CommandEvent event) {
        event.reply(localization.getMessage("command.wfstat.no"));
    }
}
