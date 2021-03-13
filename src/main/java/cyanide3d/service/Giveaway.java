package cyanide3d.service;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Giveaway {
    private static Giveaway INSTANCE;
    private Message message;
    private final Stack<String> pins = new Stack<>();
    private final List<Member> reactedUsers = new ArrayList<>();

    public static Giveaway getInstance() {
        if (INSTANCE == null) INSTANCE = new Giveaway();
        return INSTANCE;
    }

    public synchronized void start(String[] pins) {
        clear();
        this.pins.addAll(Arrays.asList(pins));
    }

    public synchronized String getReactedUserList() {
        return reactedUsers.stream()
                .map(member -> ObjectUtils.defaultIfNull(member.getNickname(), member.getUser().getName()))
                .collect(Collectors.joining("\n"));
    }

    public synchronized String getPinForMember(Member member) {
        reactedUsers.add(member);
        String pin = pins.pop();
        if (pins.empty() && message != null) {
            message.delete().queue();
            message = null;
        }
        return pin;
    }

    private void clear() {
        pins.clear();
        reactedUsers.clear();

        if (message != null)
            message.delete().queue();
    }

    public synchronized boolean isEmpty() {
        return pins.isEmpty();
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getPinList() {
        return StringUtils.join(pins, "\n");
    }

    public List<Member> getReactedUsers() {
        return reactedUsers;
    }
}
