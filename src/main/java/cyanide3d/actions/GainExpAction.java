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
            gainExpService.userLvlUp(userId);
            ChannelManagmentService.getInstance().gainExpChannel(event).sendMessage(event.getMember().getAsMention() + " получил(a) новый уровень!").queue();
        }
    }
}
