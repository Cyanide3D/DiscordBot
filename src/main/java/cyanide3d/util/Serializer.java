package cyanide3d.util;

import cyanide3d.dto.CustomCommandEntity;
import cyanide3d.model.CustomCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Serializer {

//    public String getChannels() {
//        List<TextChannel> textChannels = guild.getTextChannels();
//        StringBuilder result = new StringBuilder();
//        for (TextChannel textChannel : textChannels) {
//            result
//                    .append(textChannel.getName())
//                    .append(":")
//                    .append(textChannel.getId())
//                    .append("\n");
//        }
//        return result.toString();
//    }

    public Set<CustomCommand> deserializeCommands(List<CustomCommandEntity> entities) {
        return entities.stream()
                .map(e -> new CustomCommand(e.getCommand(), e.getBody()))
                .collect(Collectors.toSet());
    }

}
