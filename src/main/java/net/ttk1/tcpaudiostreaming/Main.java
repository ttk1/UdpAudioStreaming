package net.ttk1.tcpaudiostreaming;

public class Main {
    public static void main(String[] args) {
        int clientPort = 4567;
        String clientAddr = "127.0.0.1";

        // audio streaming test
        // server
        AudioStreamingServer asServer = new AudioStreamingServer(clientPort, clientAddr);
        Thread serverThread = new Thread(asServer);
        serverThread.start();

        // client
        AudioStreamingClient asClient = new AudioStreamingClient(clientPort);
        Thread clientThread = new Thread(asClient);
        clientThread.start();
    }
}
