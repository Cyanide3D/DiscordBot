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
import java.util.Locale;
import java.util.Random;

public class WfStats extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public WfStats(){
        this.name = "wfstat";
        this.aliases = new String[]{"warfacestats"};
        this.arguments = "[nick]";
        this.help = localization.getMessage("wfstat.help");
    }

    String[] gifList = {"https://i.gifer.com/np2.gif","https://media.discordapp.net/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif",
    "https://i.gifer.com/DriV.gif","https://i.gifer.com/Mucj.gif","https://i.gifer.com/E04.gif","https://i.gifer.com/7Wx1.gif"};
    @Override
    protected void execute(CommandEvent event) {
        if(event.getArgs()==null) {
            event.reply(localization.getMessage("wfstat.noargs"));
            return;
        }
        try {
            Player playerInfo = new ObjectMapper().readValue(new URL("http://api.warface.ru/user/stat/?name=" + event.getArgs() + "&server=3"), Player.class);
            StringBuilder stringBuilder = new StringBuilder()
                    .append("\nРанг: " + playerInfo.getRankId())
                    .append("\nКДА в PvP: " + playerInfo.getPvp())
                    .append("\nКДА в PvE: " + playerInfo.getPve())
                    .append("\nСыграно PvP матчей: " + playerInfo.getPvpAll())
                    .append("\nПобед в PvP: " + playerInfo.getPvpWins())
                    .append("\nПоражений в PvP: " + playerInfo.getPvpLost())
                    .append("\nСыграно PvE матчей: " + playerInfo.getPveAll())
                    .append("\nПобед в PvE: " + playerInfo.getPveWins())
                    .append("\nПоражений в PvE: " + playerInfo.getPveLost())
                    .append("\nНаиграно часов: " + playerInfo.getPlaytimeH())
                    .append("\nКлан: " + playerInfo.getClanName())
                    .append("\nЛучший класс в PvE: " + playerInfo.getFavoritPVE())
                    .append("\nЛучший класс в PvP: " + playerInfo.getFavoritPVP());
            MessageEmbed message = new EmbedBuilder()
                    .setImage(gifList[new Random().nextInt(gifList.length)])
                    .setThumbnail(event.getGuild().getIconUrl())
                    .setFooter("From Defiant'S with love :)")
                    .addField("Статистика игрока: " + playerInfo.getNickname(),stringBuilder.toString(),false)
                    .build();
            event.reply(message);
        } catch (IOException e) {
            event.reply(localization.getMessage("wfstat.no"));
        }
    }
}
