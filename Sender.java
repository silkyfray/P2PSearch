import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

public class Sender implements Runnable {
    private DatagramSocket socket;
    DatagramPacket message;
    BlockingQueue sendQueue;

    public Sender(BlockingQueue sQueue){
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            System.err.println("Could not create socket in Sender");
            e.printStackTrace();
        }
        sendQueue = sQueue;
    }

    public void run(){
        while(true){
            try {
                DatagramPacket sendPacket = (DatagramPacket)sendQueue.take();
                socket.send(sendPacket);
                System.out.println("Sending message...");
            } catch (IOException e) {
                System.err.println("Error sending packet in Sender");
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.err.println("Error getting packet from queue in Sender");
                e.printStackTrace();
            }


        }
    }



}