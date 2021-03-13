package cyanide3d.handlers.socket;

import cyanide3d.util.MyGuild;
import cyanide3d.model.User;
import cyanide3d.msg.ChannelModel;
import cyanide3d.msg.UserStats;
import cyanide3d.service.UserService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DataParser {


    public <T> void sendList(Socket socket, T object) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(list(object));
            objectOutputStream.close();
        } catch (Exception e) {
            System.out.println("Error to send serializable object");
        }
    }

    public <T> List<T> list(T t) {
        List<T> array = new ArrayList<>();
//        if (t instanceof ChannelModel) {
//            for (TextChannel textChannel : MyGuild.getInstance().getGuild().getTextChannels()) {
//                array.add((T) new ChannelModel(textChannel.getId(), textChannel.getName()));
//            }
//        } else if (t instanceof UserStats) {
//            for (User user : UserService.getInstance().getAllUsers()) {
//                Member member = MyGuild.getInstance().getGuild().getMemberById(user.getId());
//                if (member != null) {
//                    array.add((T) new UserStats(member.getNickname(), member.getUser().getAsTag(), member.getUser().getAvatarUrl(), user.getExperience(), user.getLevel()));
//                }
//            }
//        }
        return array;
    }
}
