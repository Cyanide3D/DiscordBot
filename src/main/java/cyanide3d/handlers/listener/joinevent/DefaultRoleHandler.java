package cyanide3d.handlers.listener.joinevent;

import cyanide3d.repository.service.DefaultRoleService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import java.util.List;

public class DefaultRoleHandler implements JoinEventHandler{
    @Override
    public void execute(GuildMemberJoinEvent event) {
        DefaultRoleService service = DefaultRoleService.getInstance();
        final List<String> roleIDs = service.getAllRoleIDs(event.getGuild().getId());
        final Member member = event.getMember();

        for (String roleID : roleIDs) {
            Role role = event.getGuild().getRoleById(roleID);
            if (role != null) {
                event.getGuild()
                        .addRoleToMember(member, role)
                        .queue();
            }
        }
    }
}
