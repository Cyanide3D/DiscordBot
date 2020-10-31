package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import conf.UserAcessToCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ListUsers extends Command {

    ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

    public ListUsers(){
        this.name = "modlist";
        this.aliases = new String[]{"modlist"};
        this.help = bundle.getString("modlist.help");
    }
    @Override
    protected void execute(CommandEvent e) {
        UserAcessToCommand usrAccess = UserAcessToCommand.getInstance();
        EmbedBuilder eb = new EmbedBuilder();
        String perm="";
        List<Member> usr = e.getGuild().getMembers();
        StringBuilder creators = new StringBuilder();
        StringBuilder owners = new StringBuilder();
        StringBuilder moderators = new StringBuilder();

        for(Member user : usr){
            if(usrAccess.userIDs.containsKey(user.getId())){
                if(usrAccess.userIDs.get(user.getId()) == 0)
                    creators.append(user.getNickname() + " (" + user.getUser().getName() + ")\n");
                if(usrAccess.userIDs.get(user.getId()) == 1)
                    owners.append(user.getNickname() + " (" + user.getUser().getName() + ")\n");
                if(usrAccess.userIDs.get(user.getId()) == 2)
                    moderators.append(user.getNickname() + " (" + user.getUser().getName() + ")\n");
            }
        }
        eb.setAuthor(bundle.getString("modlist.embed.author"));
        eb.setThumbnail(e.getGuild().getIconUrl());
        eb.setColor(Color.ORANGE);
        eb.setFooter(bundle.getString("modlist.embed.footer"));
        eb.addField(bundle.getString("modlist.embed.listowner"),creators.toString(),false);
        eb.addField(bundle.getString("modlist.embed.listlead"),owners.toString(),false);
        eb.addField(bundle.getString("modlist.embed.listmod"),moderators.toString(),false);
        e.reply(eb.build());
    }
}
