package events;

import conf.UserAcessToCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class FillUserIDs extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        UserAcessToCommand usrAccess = UserAcessToCommand.getInstance();
        if(usrAccess.isEmpty()){
            usrAccess.setUserIDs();
        }
    }
}
