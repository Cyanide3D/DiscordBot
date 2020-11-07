package cyanide3d.actions;
import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.GainExpService;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;



public class GainExpAction implements Action{
    GuildMessageReceivedEvent event;

    public GainExpAction(GuildMessageReceivedEvent event) {
        this.event = event;
    }
    @Override
    public void execute() {
        GainExpService gainExpService = GainExpService.getInstance();
        String userId = event.getAuthor().getId();
        gainExpService.increaseExp(userId);

        int userLvl = Integer.parseInt(gainExpService.getUsersLvl().get(userId));
        int userExp = Integer.parseInt(gainExpService.getUsersExp().get(userId));

        if(userExp > 15+userLvl*2){
            System.out.println(userLvl);
            gainExpService.userLvlUp(userId);
            checkUserLevel(userLvl+1);
            ChannelManagmentService.getInstance().gainExpChannel(event).sendMessage(event.getMember().getAsMention() + " получил(a) новый уровень!").queue();
        }
    }

    public void checkUserLevel(int userLvl){
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
                addRoleName = "Всезнающий";
                deleteRoleName="Опытный";
                break;
            case 40:
                addRoleName = "Гений";
                deleteRoleName="Всезнающий";
                break;
            case 50:
                addRoleName = "Учитель";
                deleteRoleName="Гений";
                break;
            case 70:
                addRoleName = "Оратор";
                deleteRoleName="Учитель";
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
