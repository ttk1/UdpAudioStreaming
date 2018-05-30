package net.ttk1.tcpaudiostreaming;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class AudioStreamingClient implements Runnable {
    private final int clientPort;

    public AudioStreamingClient(int clientPort) {
        this.clientPort = clientPort;
    }

    @Override
    public void run() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(clientPort);

            //PCM_SIGNED 44100.0 Hz, 16 bit, stereo, 4 bytes/frame, little-endian
            //前もって調べておく
            AudioFormat af = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2,4,44100.0F,false);

            DataLine.Info dataineInfo = new DataLine.Info(SourceDataLine.class, af);
            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataineInfo);
            sourceDataLine.open();
            sourceDataLine.start();

            byte[] data = new byte[100];
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length);

            while (true) {
                datagramSocket.receive(datagramPacket);
                sourceDataLine.write(data, 0, datagramPacket.getLength());
            }
            //sourceDataLine.drain();
            //sourceDataLine.stop();
            //sourceDataLine.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
