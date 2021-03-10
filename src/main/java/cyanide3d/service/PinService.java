package cyanide3d.service;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.apache.commons.lang3.StringUtils;

import java.awt.desktop.UserSessionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PinService {
    private static PinService INSTANCE;
    Message parseMessage;
    List<String> pins = Collections.synchronizedList(new ArrayList<>());
    List<Member> reactedUser = Collections.synchronizedList(new ArrayList<>());


    public List<Member> getReactedUser() {
        return reactedUser;
    }

    public void init(String[] pins) {
        clear();
        this.pins.addAll(Arrays.asList(pins));
    }

    public Message getParseMessage() {
        return parseMessage;
    }

    public void setParseMessage(Message parseMessage) {
        this.parseMessage = parseMessage;
    }

    public String getPinList() {
        return StringUtils.join(pins, "\n");
    }

    public String getReactedUserList() {
        final List<String> nicknames = reactedUser.stream()
                .map(member -> member.getNickname() == null ? member.getUser().getName() : member.getNickname())
                .collect(Collectors.toList());
        return StringUtils.join(nicknames, "\n");
    }

    public boolean isEmptyPinPool() {
        return pins.isEmpty();
    }

    public String getPinForMember(Member member) {
        reactedUser.add(member);
        return pins.remove(pins.size() - 1);
    }

    public void isEndDistribution() {
        if (pins.isEmpty() && parseMessage != null) {
            parseMessage.delete().queue();
            parseMessage = null;
        }
    }

    public void clear() {
        pins.clear();
        reactedUser.clear();

        if (parseMessage != null)
            parseMessage.delete().queue();
    }

    public static PinService getInstance() {
        if (INSTANCE == null) INSTANCE = new PinService();
        return INSTANCE;
    }
}
