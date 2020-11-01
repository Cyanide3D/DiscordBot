package cyanide3d.events;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Dialog extends ListenerAdapter {

    EventWaiter waiter;

    public Dialog(EventWaiter waiter){
        this.waiter = waiter;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        //WaitForEvent(Event.class, e -> if, r -> execute, timeout, timeunit, () - > ifnoanswer)
        if(!event.getMessage().getMentionedMembers().isEmpty()) {
            Member usr = event.getMessage().getMentionedMembers().get(0);
            if (usr.getUser().getName().equalsIgnoreCase(event.getJDA().getSelfUser().getName())) {
                event.getChannel().sendMessage("Шо надо?").queue();
                waiter.waitForEvent(GuildMessageReceivedEvent.class,e->{
                    return true;
                    },e->{
                    event.getChannel().sendMessage("4mo").queue();
                    e.getChannel().sendMessage("4mo").queue();
                    e.getAuthor().openPrivateChannel().queue((msg)->{
                        msg.sendMessage("4mo").queue();
                    });
                });
            }
        }
    }
}
