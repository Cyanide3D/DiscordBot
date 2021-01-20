package cyanide3d.handlers;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.net.Socket;

public class VerifyMessageHandler implements Runnable {
    Socket socket;

    public VerifyMessageHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            StringBuilder message = new StringBuilder();
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int read;
            while((read = inputStream.read(bytes)) != -1) {
                String output = new String(bytes, 0, read);
                if (!StringUtils.isEmpty(output)){
                    message.append(output);
                }
            }
            new FromVkToDiscordMessageHandler(message.toString()).send();
        } catch (Exception e) {
            System.out.println("Error to verify message");
        }
    }
}
