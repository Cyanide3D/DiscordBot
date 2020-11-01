package cyanide3d.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class EventRequest extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

        TextChannel channel = e.getChannel();
        String names[] = {"Имя:","Кол-во лет:","Игровой ник:","Средний онлайн:","Ранг:","Ссылка на ВК:","Разница во времени от МСК:","Пригласивший игрок:"};
        EmbedBuilder eb = new EmbedBuilder();
        TextChannel postChannel = e.getJDA().getTextChannelById("664814068036665354");
        String needChID = "664814068036665354";
        String channelID = channel.getId();
        StringBuilder sb = new StringBuilder();
        String[] msgLines;
        if(channelID.equalsIgnoreCase(needChID) && !e.getAuthor().isBot()){

            msgLines = e.getMessage().getContentRaw().split("\n");
            if(msgLines.length==8) {
                eb.setColor(Color.ORANGE);
                eb.setAuthor(e.getAuthor().getAsTag(),null,e.getAuthor().getAvatarUrl());
                eb.setTitle(bundle.getString("eventrequest.embed.title"));
                eb.setThumbnail(e.getAuthor().getAvatarUrl());
                eb.setImage("https://media3.giphy.com/media/WV4YdUfCxDfwA5MH0Q/giphy.gif?cid=ecf05e474fb24ae3998bcd07410214fdbc0ba947138f297a&rid=giphy.gif");
                eb.setFooter(bundle.getString("eventrequest.embed.footer"));
                for (int i = 0; i < msgLines.length; i++) {
                    String[] msgW = msgLines[i].split("");
                    try {
                        for (int j = 2; j < msgW.length; j++) {
                            sb.append(msgW[j] + " ");
                        }
                    }catch (IndexOutOfBoundsException ex){
                        e.getMessage().delete().queue();
                        channel.sendMessage(bundle.getString("eventrequest.embed.noform")).queue();
                        return;
                    }
                    String tmp2 = sb.toString();
                    eb.addField(names[i], tmp2.replaceAll("\\s", ""), false);
                    sb.delete(0, sb.length() - 1);
                }
                eb.addField("ID: ",e.getAuthor().getId(),false);
                e.getMessage().delete().queue();
                postChannel.sendMessage(eb.build()).queue();
            }else{
                    e.getMessage().delete().queue();
                    channel.sendMessage(bundle.getString("eventrequest.embed.noformfull") + e.getGuild().getOwner().getAsMention()).queue();
            }
        }
    }
}
