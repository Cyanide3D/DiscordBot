package cyanide3d.handlers.socket;

import net.dv8tion.jda.api.JDA;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class MessageDispatcher implements Runnable {
    Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);
    Socket socket;
    private final JDA jda;

    public MessageDispatcher(Socket socket, JDA jda) {
        this.socket = socket;
        this.jda = jda;
    }

    @Override
    public void run() {
        try {
            String message = takeRequest();
            String output = dispatch(message);
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

    private String dispatch(String message) {
        SocketHandler handler;
        if (message.startsWith("23qweqweasdcasd12321412123123function123eqwe123")) {
            handler = new WirelessControlHandler(message);
        } else {
            handler = new DiscordHandler(message, jda);
        }
        return handler.handle();
    }

}
