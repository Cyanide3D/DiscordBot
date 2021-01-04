package cyanide3d.handlers;

import cyanide3d.conf.Config;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class FromDiscordToVkMessageHandler {
    Socket socket;
    BufferedWriter bufferedWriter;
    public FromDiscordToVkMessageHandler() {
        try {
            int port = Integer.parseInt(Config.getInstance().getVkPort());
            socket = new Socket("188.134.66.216", port);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void send(String message) {
        try {
            bufferedWriter.write(message + "\r");
            bufferedWriter.close();
        } catch (Exception e){
            System.out.println("Error send message...");
            e.printStackTrace();
        }
    }
}
