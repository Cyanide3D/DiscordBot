package cyanide3d.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.model.Player;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

public class WfStats extends Command {

    private final Localization localization = Localization.getInstance();

    public WfStats() {
        this.name = "wfstat";
    }

    final String[] gifList = {"https://i.gifer.com/np2.gif", "https://media.discordapp.net/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif",
            "https://i.gifer.com/DriV.gif", "https://i.gifer.com/Mucj.gif", "https://i.gifer.com/E04.gif", "https://i.gifer.com/7Wx1.gif"};
    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs() == null) {
            event.reply(localization.getMessage("wfstat.noargs"));
            return;
        }
        try {
            Player playerInfo = new ObjectMapper().readValue(new URL("http://api.warface.ru/user/stat/?name=" + URLEncoder.encode(event.getArgs(), "utf-8") + "&server=3"), Player.class);
            MessageEmbed message = new EmbedBuilder()
                    .setImage(gifList[new Random().nextInt(gifList.length)])
                    .setThumbnail(event.getGuild().getIconUrl())
                    .setFooter("From Defiant'S with love :)")
                    .addField("Статистика игрока: " + playerInfo.getNickname(), "\nРанг: " + playerInfo.getRankId() + "\nНаиграно часов: " + playerInfo.getPlaytimeH() + "\nКлан: " + playerInfo.getClanName(), false)
                    .addField("Информация по PvE:", "\nКДА: " + playerInfo.getPve() + "\nСыграно матчей: " + playerInfo.getPveAll() + "\nПобеды: " + playerInfo.getPveWins() + "\nПоражения: " + playerInfo.getPveLost() + "\nУбийства: " + playerInfo.getPveKill() + "\nСмертей: " + playerInfo.getPveDeath() + "\nЛучший класс: " + playerInfo.getFavoritPVE() + "\nУбийств союзников: " + playerInfo.getPveFriendlyKills(), true)
                    .addField("Информация по PvP:", "\nКДА: " + playerInfo.getPvp() + "\nСыграно матчей: " + playerInfo.getPvpAll() + "\nПобеды: " + playerInfo.getPvpWins() + "\nПоражения: " + playerInfo.getPvpLost() + "\nУбийства: " + playerInfo.getKills() + "\nСмертей: " + playerInfo.getDeath() + "\nЛучший класс: " + playerInfo.getFavoritPVP(), true)
                    .build();
            event.reply(message);
        } catch (IOException e) {
            event.reply(localization.getMessage("wfstat.no"));
        }
    }
}
