package cyanide3d.handlers;

import java.net.Socket;

public class VerifyMessageHandler implements Runnable{
    String message;
    Socket socket;

    public VerifyMessageHandler(String message, Socket socket) {
        this.message = message;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
//            SendMessageHandler sendMessageHandler = new SendMessageHandler();
//            if ("channels".equals(message)) {
//                sendMessageHandler.sendList(socket, new ChannelModel());
//            } else if ("leaderboard".equals(message)) {
//                sendMessageHandler.sendList(socket, new UserStats());
//            } else {
                new FromVkToDiscordMessageHandler(message).send();
//            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error to verify message");
        }
    }
}
