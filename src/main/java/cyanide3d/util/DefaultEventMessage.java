package cyanide3d.util;

import java.util.Random;

public class DefaultEventMessage {

    private static final String[] joinGifs = {"https://cdn.discordapp.com/attachments/573773778480398337/771325629491707924/tenor.gif",
            "https://cdn.discordapp.com/attachments/573773778480398337/771325641009135626/tenor_1.gif",
            "https://i.gifer.com/EIGB.gif",
            "https://i.gifer.com/D85T.gif",
            "https://i.gifer.com/Pvm.gif"};

    private static final String[] leaveGifs = {"https://media.discordapp.net/attachments/614472783715500052/767371392466812938/tenor.gif.gif",
            "https://media.discordapp.net/attachments/614472783715500052/767371396354408458/good_bye_2.gif.gif",
            "https://i.gifer.com/53HC.gif", "https://i.gifer.com/9TEx.gif", "https://i.gifer.com/7A25.gif",
            "https://cdn.discordapp.com/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif"};

    private static final String[] joinTitles = {"Добро пожаловать на наш сервер!"};

    private static final String[] joinBodies = {"${username} приземлился к нам на сервер!", "${username} зашёл к нам в гости!"};

    private static final String[] leaveTitles = {"${tag} покинул наш сервер :("};

    private static final String[] leaveBodies = {"Надеюсь, еще увидимся!"};

    public static String getEventTitle(ActionType type) {
        return type.equals(ActionType.JOIN)
                ? getRandArrayItem(joinTitles)
                : getRandArrayItem(leaveTitles);
    }

    public static String getEventBody(ActionType type) {
        return type.equals(ActionType.JOIN)
                ? getRandArrayItem(joinBodies)
                : getRandArrayItem(leaveBodies);
    }

    public static String getEventImage(ActionType type) {
        return type.equals(ActionType.JOIN)
                ? getRandArrayItem(joinGifs)
                : getRandArrayItem(leaveGifs);
    }

    private static String getRandArrayItem(String[] array) {
        return array[new Random().nextInt(array.length)];
    }

}
