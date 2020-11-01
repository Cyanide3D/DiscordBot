package cyanide3d.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class WfStats extends Command {

    ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

    public WfStats(){
        this.name = "wfstat";
        this.aliases = new String[]{"warfacestats"};
        this.arguments = "[nick]";
        this.help = bundle.getString("wfstat.help");
    }

    String[] gifList = {"https://i.gifer.com/np2.gif","https://media.discordapp.net/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif",
    "https://i.gifer.com/DriV.gif","https://i.gifer.com/Mucj.gif","https://i.gifer.com/E04.gif","https://i.gifer.com/7Wx1.gif"};
    @Override
    protected void execute(CommandEvent commandEvent) {
        Random rnd = new Random();
        EmbedBuilder eb = new EmbedBuilder();
        if(commandEvent.getArgs()!=null) {
            try {
                Player playerInfo = new ObjectMapper().readValue(new URL("http://api.warface.ru/user/stat/?name=" + commandEvent.getArgs() + "&server=3"), Player.class);
                eb.setTitle("Статистика игрока: " + commandEvent.getArgs());
                eb.setImage(gifList[rnd.nextInt(gifList.length)]);
                eb.setThumbnail(commandEvent.getGuild().getIconUrl());
                eb.addField("","Ник игрока: " + playerInfo.getNickname(),false);
                eb.addField("","Ранг: " + playerInfo.getRankId(),false);
                eb.addField("","КДА в PvP: " + playerInfo.getPvp(),false);
                eb.addField("","КДА в PvE: " + playerInfo.getPve(),false);
                eb.addField("","Сыграно PvP матчей: " + playerInfo.getPvpAll(),false);
                eb.addField("","Сыграно PvE матчей: " + playerInfo.getPveAll(),false);
                eb.addField("","Наиграно часов: " + playerInfo.getPlaytimeH(),false);
                eb.addField("","Клан: " + playerInfo.getClanName(),false);
                eb.addField("","Лучший класс в PvE: " + playerInfo.getFavoritPVE(),false);
                eb.addField("","Лучший класс в PvP: " + playerInfo.getFavoritPVP(),false);
                commandEvent.reply(eb.build());
            } catch (IOException e) {
                commandEvent.reply(bundle.getString("wfstat.no"));
            }
        }else{
            commandEvent.reply(bundle.getString("wfstat.noargs"));
        }
    }
}
