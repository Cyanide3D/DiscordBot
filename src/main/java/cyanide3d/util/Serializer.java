package cyanide3d.util;

import cyanide3d.dto.CustomCommandEntity;
import cyanide3d.model.CustomCommand;
import cyanide3d.util.MyGuild;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Serializer {

    private final Guild guild = MyGuild.getInstance().getGuild();

    public String getChannels() {
        List<TextChannel> textChannels = guild.getTextChannels();
        StringBuilder result = new StringBuilder();
        for (TextChannel textChannel : textChannels) {
            result
                    .append(textChannel.getName())
                    .append(":")
                    .append(textChannel.getId())
                    .append("\n");
        }
        return result.toString();
    }

    public List<CustomCommand> deserializeCommands(List<CustomCommandEntity> entities) {
        return entities.stream()
                .map(e -> new CustomCommand(e.getId(), e.getBody()))
                .collect(Collectors.toList());
    }

}
