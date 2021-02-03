package cyanide3d.handlers.socket;

import cyanide3d.conf.Logging;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
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
            String message = takeRequest();
            String output = messageDispatcher(message);
            if (output != null) {
                writeResponse(output);
            }
            System.out.println(message);

        } catch (Exception e) {
            System.out.println("Error to verify message");
            e.printStackTrace();
            logger.log(Level.WARNING, "VerifyMessageHandler, error to verify message", e);

        }
    }

    @NotNull
    private String takeRequest() throws IOException {
        InputStream inputStream = socket.getInputStream();
        StringBuilder message = new StringBuilder();
        byte[] bytes = new byte[1024];
        int read;
        while ((read = inputStream.read(bytes)) != -1) {
            String output = new String(bytes, 0, read);
            if (!StringUtils.isEmpty(output)) {
                message.append(output);
            }
        }
        return message.toString();
    }

    private void writeResponse(String output) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write(output);
        bufferedWriter.flush();
        socket.shutdownOutput();
    }

    public String messageDispatcher(String message) {
        SocketHandler handler;
        if (message.startsWith("23qweqweasdcasd12321412123123function123eqwe123")) {
            handler = new WirelessControlHandler(message);
        } else {
            handler = new FromVkToDiscordMessageHandler(message);
        }
        return handler.handle();
    }

}
