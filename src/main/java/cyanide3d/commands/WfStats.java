package cyanide3d.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

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
            MessageEmbed message = new EmbedBuilder()
                    .setTitle("Статистика игрока: " + event.getArgs())
                    .setImage(gifList[new Random().nextInt(gifList.length)])
                    .setThumbnail(event.getGuild().getIconUrl())
                    .addField(null,"Ник игрока: " + playerInfo.getNickname(),false)
                    .addField(null,"Ранг: " + playerInfo.getRankId(),false)
                    .addField(null,"КДА в PvP: " + playerInfo.getPvp(),false)
                    .addField(null,"КДА в PvE: " + playerInfo.getPve(),false)
                    .addField(null,"Сыграно PvP матчей: " + playerInfo.getPvpAll(),false)
                    .addField(null,"Сыграно PvE матчей: " + playerInfo.getPveAll(),false)
                    .addField(null,"Наиграно часов: " + playerInfo.getPlaytimeH(),false)
                    .addField(null,"Клан: " + playerInfo.getClanName(),false)
                    .addField(null,"Лучший класс в PvE: " + playerInfo.getFavoritPVE(),false)
                    .addField(null,"Лучший класс в PvP: " + playerInfo.getFavoritPVP(),false)
                    .build();
            event.reply(message);
        } catch (IOException e) {
            event.reply(localization.getMessage("wfstat.no"));
        }
    }
}
