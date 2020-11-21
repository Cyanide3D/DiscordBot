package cyanide3d.service;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

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

    public void setReactedUser(Member user) {
        reactedUser.add(user);
    }

    public void setPins(String[] pins) {
        this.pins.clear();
        this.pins.addAll(Arrays.stream(pins).collect(Collectors.toList()));
    }

    public void removePin(int index) {
        pins.remove(index);
    }

    public Message getParseMessage() {
        return parseMessage;
    }

    public void setParseMessage(Message parseMessage) {
        this.parseMessage = parseMessage;
    }

    public List<String> getPins() {
        return pins;
    }

    public static PinService getInstance() {
        if (INSTANCE == null) INSTANCE = new PinService();
        return INSTANCE;
    }
}
