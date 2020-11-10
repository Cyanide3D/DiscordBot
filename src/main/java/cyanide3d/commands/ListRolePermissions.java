package cyanide3d.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Permission;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.Map;

public class ListRolePermissions {
    final Map<String, Permission> roleID = PermissionService.getInstance().giveRoleList();

    public void listRolePermission(CommandEvent e) {
        StringBuilder owner = new StringBuilder();
        StringBuilder stmod = new StringBuilder();
        StringBuilder mod = new StringBuilder();
        List<Member> users = e.getGuild().getMembers();
        for (Member user : users) {
            List<Role> userRoles = user.getRoles();
            for (Role userRole : userRoles) {
                if (roleID.containsKey(userRole.getId())) {
                    if (roleID.get(userRole.getId()).equals(Permission.OWNER))
                        owner.append(user.getNickname()).append(" | ").append(user.getUser().getName()).append("\n");
                    if (roleID.get(userRole.getId()).equals(Permission.ADMIN))
                        stmod.append(user.getNickname()).append(" | ").append(user.getUser().getName()).append("\n");
                    if (roleID.get(userRole.getId()).equals(Permission.MODERATOR))
                        mod.append(user.getNickname()).append(" | ").append(user.getUser().getName()).append("\n");
                }
            }
        }
        e.reply(EmbedTemplates.listUsersWithRoles(owner.toString(), stmod.toString(), mod.toString()));
    }
}
