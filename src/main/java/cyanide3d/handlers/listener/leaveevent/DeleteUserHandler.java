package cyanide3d.handlers.listener.leaveevent;

import cyanide3d.service.UserService;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

public class DeleteUserHandler implements LeaveEventHandler{
    @Override
    public void execute(GuildMemberRemoveEvent event) {
        User user = event.getUser();
        UserService.getInstance()
                .deleteUser(user.getId(), event.getGuild().getId());
    }
}
