package cyanide3d.handlers.socket;

import cyanide3d.conf.Config;
import cyanide3d.filters.SocketFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class VkSender {

    private BufferedWriter bufferedWriter;
    private final Logger logger = LoggerFactory.getLogger(VkSender.class);


    public VkSender() {
        try {
            int port = Integer.parseInt(Config.getInstance().getVkPort());
            Socket socket = new Socket("188.134.66.216", port);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e){
            logger.error("Error connect to VK socket...", e);
        }
    }

    public void send(String message) {
        try {
           // bufferedWriter.write(new SocketFilter(message).toVk() + "\r"); //FIXME
            bufferedWriter.write(message + "\r");
            bufferedWriter.close();
        } catch (Exception e){
            logger.error("Error send message to VK socket...", e);
        }
    }
}
