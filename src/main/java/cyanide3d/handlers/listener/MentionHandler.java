package cyanide3d.handlers.listener;

import cyanide3d.model.RoleUse;
import cyanide3d.service.MessageService;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MentionHandler implements ListenerHandler {

    private final GuildMessageReceivedEvent event;

    public MentionHandler(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    @Override
    public void handle() {
        MessageService.getInstance().add(new cyanide3d.model.Message(event.getMessageId(), event.getMessage().getContentRaw()));
        List<Role> roles = event.getMessage().getMentionedRoles();
        if (!roles.isEmpty()) {
            for (Role role : roles) {
                MessageService.getInstance().add(new RoleUse(role.getId(), new SimpleDateFormat("dd:MM:yyyy").format(new Date()), "1"));
            }
        }
    }
}
