package cyanide3d.listener;

import cyanide3d.conf.Config;
import cyanide3d.handlers.VerifyMessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketMessageListener extends Thread {
    ServerSocket serverSocket;
    BufferedReader buffer;

    @Override
    public void run() {
        try{
            serverSocket = new ServerSocket(Integer.parseInt(Config.getInstance().getListenerPort()));
        } catch (Exception e){
            System.out.println("Socket is down nahoy");
            run();
        }
        while (true) {
            try {
                listener();
            } catch (IOException e) {
                System.out.println("Thread interrupt, try again...");
            }
        }
    }

    public void listener() throws IOException {
        Socket socket = serverSocket.accept();
        buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String msg = buffer.readLine();
        messageHanding(socket, msg);
        buffer.close();
    }

    private void messageHanding(Socket socket, String msg) {
        ExecutorService executor = Executors.newCachedThreadPool();
        VerifyMessageHandler verifyMessageHandler = new VerifyMessageHandler(msg, socket);
        executor.execute(verifyMessageHandler);
    }
}
