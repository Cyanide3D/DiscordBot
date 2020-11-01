package cyanide3d.events;

import cyanide3d.conf.UserAccessToCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class FillBD extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        UserAccessToCommand usr = UserAccessToCommand.getInstance();
        BadWordsEvent bwe = BadWordsEvent.getInstance();

        if(usr.rolesIDs.size()==0){
            usr.setRolesIDs();
        }
        if(bwe.badWords.size()==0){
            bwe.setBadWords();
        }
    }
}
