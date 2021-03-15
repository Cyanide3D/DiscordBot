package cyanide3d.handlers.listener;

import cyanide3d.dto.MessageEntity;
import cyanide3d.dto.RoleEntity;
import cyanide3d.model.RoleUse;
import cyanide3d.service.MessageService;
import cyanide3d.service.RoleService;
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
        MessageService messageService = MessageService.getInstance();
        RoleService roleService = RoleService.getInstance();
        messageService.add(event.getMessageId(), event.getMessage().getContentRaw(), event.getGuild().getId());
        List<Role> roles = event.getMessage().getMentionedRoles();
        if (!roles.isEmpty()) {
            for (Role role : roles) {
                roleService.add(role.getName(), new SimpleDateFormat("dd:MM:yyyy").format(new Date()), event.getGuild().getId());
            }
        }
    }
}
