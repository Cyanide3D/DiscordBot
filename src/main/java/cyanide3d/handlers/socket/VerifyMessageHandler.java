package cyanide3d.handlers.socket;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class VerifyMessageHandler implements Runnable {
    Logger logger = LoggerFactory.getLogger(VerifyMessageHandler.class);
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
            } else {
                socket.close();
            }
        } catch (Exception e) {
            logger.error("Error to verify message", e);

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
