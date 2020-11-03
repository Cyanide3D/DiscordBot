package cyanide3d.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Permission;
import cyanide3d.exceprtion.UnsupportedPermissionException;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.Map;

public class ListRolePermissions {
    Map<String, Permission> roleID = PermissionService.getInstance().giveRoleList();

    public void listRolePermission(CommandEvent e) {
        StringBuilder owner = new StringBuilder();
        StringBuilder stmod = new StringBuilder();
        StringBuilder mod = new StringBuilder();
        List<Member> users = e.getGuild().getMembers();
        for (int i = 0; i < users.size(); i++) {
            List<Role> userRoles = users.get(i).getRoles();
            for (int j = 0; j < userRoles.size(); j++) {
                if (roleID.containsKey(userRoles.get(j).getId())) {
                    if (roleID.get(userRoles.get(j).getId()).equals(Permission.OWNER))
                        owner.append(users.get(i).getNickname() + users.get(i).getUser().getName() + "\n");
                    if (roleID.get(userRoles.get(j).getId()).equals(Permission.ADMIN))
                        stmod.append(users.get(i).getNickname() + users.get(i).getUser().getName() + "\n");
                    if (roleID.get(userRoles.get(j).getId()).equals(Permission.MODERATOR))
                        mod.append(users.get(i).getNickname() + users.get(i).getUser().getName() + "\n");
                }
            }
        }
        e.reply(EmbedTemplates.listUsersWithRoles(owner.toString(), stmod.toString(), mod.toString()));
    }
}
