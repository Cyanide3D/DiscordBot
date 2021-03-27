package cyanide3d.handlers.listener.receivedmessage;

import cyanide3d.repository.service.RoleService;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MentionHandler implements ReceivedMessageHandler {

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        RoleService roleService = RoleService.getInstance();
        List<Role> roles = event.getMessage().getMentionedRoles();
        for (Role role : roles) {
            roleService.addRoleByDate(role.getName(), new SimpleDateFormat("dd:MM:yyyy").format(new Date()), event.getGuild().getId());
        }
    }
}
