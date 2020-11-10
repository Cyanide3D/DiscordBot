package cyanide3d.actions;

import cyanide3d.model.User;
import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.UserService;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


public class GainExpAction implements Action {
    final GuildMessageReceivedEvent event;

    public GainExpAction(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    @Override
    public void execute() {
        UserService userService = UserService.getInstance();
        String userId = event.getAuthor().getId();
        User user = userService.incrementExp(userId);
        if (user.getExperience() == 0) {
            setLevelRole(user.getLevel());
            ChannelManagmentService.getInstance().gainExpChannel(event).sendMessage(event.getMember().getAsMention() + " получил(a) новый уровень!").queue();
        }
    }

    public void setLevelRole(int userLvl) {
        String addRoleName;
        String deleteRoleName = null;
        switch (userLvl) {
            case 1:
                addRoleName = "Нуб";
                break;
            case 10:
                deleteRoleName = "Нуб";
                addRoleName = "Начинающий";
                break;
            case 20:
                deleteRoleName = "Начинающий";
                addRoleName = "Опытный";
                break;
            case 30:
                deleteRoleName = "Опытный";
                addRoleName = "Учитель";
                break;
            case 40:
                deleteRoleName = "Учитель";
                addRoleName = "Гений";
                break;
            case 50:
                deleteRoleName = "Гений";
                addRoleName = "Всезнающий";
                break;
            case 70:
                deleteRoleName = "Всезнающий";
                addRoleName = "Оратор";
                break;
            case 80:
                deleteRoleName = "Оратор";
                addRoleName = "Тот за кем пойдут";
                break;
            case 90:
                deleteRoleName = "Тот за кем пойдут";
                addRoleName = "Без меня никак";
                break;
            default:
                return;
        }
        try {
            if (deleteRoleName != null) {
                event.getGuild().removeRoleFromMember(event.getAuthor().getId(), event.getGuild().getRolesByName(deleteRoleName, true).get(0)).queue();
            }
        } catch (NullPointerException e) {
            System.out.println("Какой то пидорас роль с себя снял.");
        }
            event.getGuild().addRoleToMember(event.getAuthor().getId(), event.getGuild().getRolesByName(addRoleName, true).get(0)).queue();
    }
}
