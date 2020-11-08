package cyanide3d.actions;
import cyanide3d.model.User;
import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.UserService;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;


public class GainExpAction implements Action{
    GuildMessageReceivedEvent event;

    public GainExpAction(GuildMessageReceivedEvent event) {
        this.event = event;
    }
    @Override
    public void execute() {
        UserService userService = UserService.getInstance();
        List<User> users = userService.getAllUsers();
        String userId = event.getAuthor().getId();
        userService.increaseExp(userId);
        int userLvl = 0;
        int userExp = 0;
        for (User user : users) {
            if (user.getId().equals(userId)) {
                userLvl = user.getLevel();
                userExp = user.getExperience();
            }
        }

        if (userExp > 15 + userLvl * 2) {
            System.out.println(userLvl);
            userService.userLvlUp(userId);
            lvlupAction(userLvl + 1);
            ChannelManagmentService.getInstance().gainExpChannel(event).sendMessage(event.getMember().getAsMention() + " получил(a) новый уровень!").queue();
        }
    }

    public void lvlupAction(int userLvl){
        String addRoleName;
        String deleteRoleName="";
        switch (userLvl){
            case 10:
                addRoleName = "Начинающий";
                break;
            case 20:
                addRoleName = "Опытный";
                deleteRoleName="Начинающий";
                break;
            case 30:
                addRoleName = "Учитель";
                deleteRoleName="Опытный";
                break;
            case 40:
                addRoleName = "Гений";
                deleteRoleName="Учитель";
                break;
            case 50:
                addRoleName = "Всезнающий";
                deleteRoleName="Гений";
                break;
            case 70:
                addRoleName = "Оратор";
                deleteRoleName="Всезнающий";
                break;
            case 80:
                addRoleName = "Тот за кем пойдут";
                deleteRoleName="Оратор";
                break;
            case 90:
                addRoleName = "Без меня не как";
                deleteRoleName="Тот за кем пойдут";
                break;
            default:
                return;
        }
        if (!deleteRoleName.isEmpty()) {
            event.getGuild().removeRoleFromMember(event.getAuthor().getId(), event.getGuild().getRolesByName(deleteRoleName,true).get(0)).queue();
        }
        event.getGuild().addRoleToMember(event.getAuthor().getId(), event.getGuild().getRolesByName(addRoleName,true).get(0)).queue();
    }
}
