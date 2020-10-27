package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.io.IOException;
import java.net.URL;

public class WfStats extends Command {
    public WfStats(){
        this.name = "wfstat";
        this.aliases = new String[]{"warfacestats"};
        this.arguments = "[nick]";
        this.help = "Команда для получения общей статистики игрока.";
    }
    @Override
    protected void execute(CommandEvent commandEvent) {
        EmbedBuilder eb = new EmbedBuilder();
        if(commandEvent.getArgs()!=null) {
            try {
                Player playerInfo = new ObjectMapper().readValue(new URL("http://api.warface.ru/user/stat/?name=" + commandEvent.getArgs() + "&server=3"), Player.class);
                eb.setTitle("Статистика игрока: " + commandEvent.getArgs());
                eb.setImage("https://media.discordapp.net/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif");
                eb.setThumbnail(commandEvent.getGuild().getIconUrl());
                eb.addField("","Ник игрока: " + playerInfo.getNickname(),false);
                eb.addField("","Ранг: " + playerInfo.getRankId(),false);
                eb.addField("","КДА: " + playerInfo.getPvp(),false);
                eb.addField("","Сыграно PvP матчей: " + playerInfo.getPvpAll(),false);
                eb.addField("","Наиграно часов: " + playerInfo.getPlaytimeH(),false);
                eb.addField("","Клан: " + playerInfo.getClanName(),false);
                eb.addField("","Лучший класс в PvE: " + playerInfo.getFavoritPVE(),false);
                eb.addField("","Лучший класс в PvP: " + playerInfo.getFavoritPVP(),false);

                commandEvent.reply(eb.build());
            } catch (IOException e) {
                commandEvent.reply("Профиль скрыт, либо такого игрока не существует :(");
            }
        }else{
            commandEvent.reply("Для получения информации по игроку введите: $wfstat [ник]");
        }
    }
}
