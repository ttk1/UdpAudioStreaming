package net.ttk1.tcpaudiostreaming;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import java.io.File;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AudioStreamingServer implements Runnable {
    private final int clientPort;
    private final String clientAddr;

    // WAV音声ファイルのパスを入れる
    private final String audioFilePath = "src\\main\\resources\\test.wav";
    private File audioFile;

    public AudioStreamingServer(int clientPort, String clientAddr) {
        this.clientPort = clientPort;
        this.clientAddr = clientAddr;
        audioFile = new File(audioFilePath);
    }

    @Override
    public void run() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);

            byte[] data = new byte[100];
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(clientAddr), clientPort);

            while (true) {
                int size = audioInputStream.read(data);
                if (size == -1) {
                    break;
                } else {
                    datagramPacket.setLength(size);
                    datagramSocket.send(datagramPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
