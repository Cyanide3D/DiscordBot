package cyanide3d.handlers.socket;

import cyanide3d.conf.Logging;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VerifyMessageHandler implements Runnable {
    Logger logger = Logging.getLogger(this.getClass());
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
            while ((read = inputStream.read(bytes)) != -1) {
                String output = new String(bytes, 0, read);
                if (!StringUtils.isEmpty(output)) {
                    message.append(output);
                }
            }
            messageDispatcher(message.toString());
        } catch (Exception e) {
            System.out.println("Error to verify message");
            logger.log(Level.WARNING, "VerifyMessageHandler, error to verify message", e);

        }
    }

    public void messageDispatcher(String message) {
        SocketHandler handler;
        if (message.startsWith("23qweqweasdcasd12321412123123function123eqwe123")){
            handler = new WirelessControlHandler(message);
        } else {
            handler = new FromVkToDiscordMessageHandler(message);
        }
        handler.handle();
    }

}
