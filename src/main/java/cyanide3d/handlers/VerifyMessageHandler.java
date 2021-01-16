package cyanide3d.handlers;

import java.io.BufferedReader;
import java.net.Socket;

public class VerifyMessageHandler implements Runnable {
    BufferedReader buffer;

    public VerifyMessageHandler(BufferedReader buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            StringBuilder message = new StringBuilder();
            while (buffer.ready()){
                message
                        .append(buffer.readLine())
                        .append("\n");
            }
            buffer.close();
            new FromVkToDiscordMessageHandler(message.toString()).send();
        } catch (Exception e) {
            System.out.println("Error to verify message");
        }
    }
}
