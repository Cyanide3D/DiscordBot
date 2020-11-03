package cyanide3d.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Permission;
import cyanide3d.exceprtion.UnsupportedPermissionException;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class RolesSettings {
//    UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
//
//    public void listRolePermission(CommandEvent e) {
//        StringBuilder owner = new StringBuilder();
//        StringBuilder stmod = new StringBuilder();
//        StringBuilder mod = new StringBuilder();
//        List<Member> users = e.getGuild().getMembers();
//        for (int i = 0; i < users.size(); i++) {
//            List<Role> userRoles = users.get(i).getRoles();
//            for (int j = 0; j < userRoles.size(); j++) {
//                if (userAccess.rolesIDs.containsKey(userRoles.get(j).getId())) {
//                    if (userAccess.rolesIDs.get(userRoles.get(j).getId()) == 0)
//                        owner.append(users.get(i).getNickname() + users.get(i).getUser().getName() + "\n");
//                    if (userAccess.rolesIDs.get(userRoles.get(j).getId()) == 1)
//                        stmod.append(users.get(i).getNickname() + users.get(i).getUser().getName() + "\n");
//                    if (userAccess.rolesIDs.get(userRoles.get(j).getId()) == 2)
//                        mod.append(users.get(i).getNickname() + users.get(i).getUser().getName() + "\n");
//                }
//            }
//        }
//        e.reply(EmbedTemplates.listUsersWithRoles(owner.toString(), stmod.toString(), mod.toString()));
//    }
}
