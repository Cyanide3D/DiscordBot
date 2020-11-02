package cyanide3d.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Permission;
import cyanide3d.conf.UserAccessToCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class RolesSettings {

    EmbedTemplates embed = new EmbedTemplates();
    UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
//    DatabaseConnection db = new DatabaseConnection(url, username, password);

    public void addRolePermission(CommandEvent e, String[] args) {
        try {
            if (args.length == 4
                    && Integer.parseInt(args[args.length - 1]) <= Permission.values().length - 1
                    && e.getMessage().getMentionedRoles().get(0) != null) {
                int permission = Integer.parseInt(args[args.length - 1]);
                Role mentionRole = e.getMessage().getMentionedRoles().get(0);
//                db.insertIDs(mentionRole.getId(), permission);
                e.reply("Роль успешно добавлена в базу данных!");
                userAccess.setRolesIDs();
            } else {
                e.reply(EmbedTemplates.SYNTAX_ERROR);
            }
        } catch (NumberFormatException ne) {
            e.reply("Неверное значение полномочий.");
        } catch (IndexOutOfBoundsException ie) {
            e.reply("Линк то где?");
        }
    }

    public void listRolePermission(CommandEvent e) {
        StringBuilder owner = new StringBuilder();
        StringBuilder stmod = new StringBuilder();
        StringBuilder mod = new StringBuilder();
        List<Member> users = e.getGuild().getMembers();
        for (int i = 0; i < users.size(); i++) {
            List<Role> userRoles = users.get(i).getRoles();
            for (int j = 0; j < userRoles.size(); j++) {
                if (userAccess.rolesIDs.containsKey(userRoles.get(j).getId())) {
                    if (userAccess.rolesIDs.get(userRoles.get(j).getId()) == 0)
                        owner.append(users.get(i).getNickname() + users.get(i).getUser().getName() + "\n");
                    if (userAccess.rolesIDs.get(userRoles.get(j).getId()) == 1)
                        stmod.append(users.get(i).getNickname() + users.get(i).getUser().getName() + "\n");
                    if (userAccess.rolesIDs.get(userRoles.get(j).getId()) == 2)
                        mod.append(users.get(i).getNickname() + users.get(i).getUser().getName() + "\n");
                }
            }
        }
        e.reply(EmbedTemplates.listUsersWithRoles(owner.toString(), stmod.toString(), mod.toString()));
    }

    public void changeRolePermission(CommandEvent e, String[] args) {
        try {
            Role role = e.getMessage().getMentionedRoles().get(0);
            if (args.length == 4 && Integer.parseInt(args[args.length - 1]) <= Permission.values().length - 1 && e.getMessage().getMentionedRoles().get(0) != null) {
                if(userAccess.rolesIDs.containsKey(role.getId())){
//                    db.changePermToID(role.getId(),Integer.parseInt(args[args.length-1]));
                    e.reply("Полномочия роли успешно изменены!");
                    userAccess.setRolesIDs();
                }else{
                    e.reply("Роль не занесена в БД.");
                }
            }
        } catch (NumberFormatException ne) {
            e.reply("Неверное значение полномочий.");
        } catch (IndexOutOfBoundsException ie) {
            e.reply("Линк то где?");
        }
    }

    public void deleteRolePermission(CommandEvent e, String[] args){
        try {
            Role role = e.getMessage().getMentionedRoles().get(0);
            if (args.length == 3 && e.getMessage().getMentionedRoles().get(0) != null) {
                if(userAccess.rolesIDs.containsKey(role.getId())){
//                    db.removeIDs(role.getId());
                    e.reply("Полномочия с роли успешно сняты!");
                    userAccess.setRolesIDs();
                }else{
                    e.reply("Роль не занесена в БД.");
                }
            }
        }catch (IndexOutOfBoundsException ie) {
            e.reply("Линк то где?");
        }
    }
}
