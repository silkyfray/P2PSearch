import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

public class Receiver implements Runnable {
    private DatagramSocket socket;
    private Controller controller;
    private BlockingQueue receiveQueue;

    public Receiver(DatagramSocket socket,BlockingQueue rQueue){
        this.socket = socket;
        this.controller = controller;
        receiveQueue = rQueue;
    }

    public void run(){
        while(true){
            byte[] buf = new byte[NetworkProperties.MTU];
            DatagramPacket message = new DatagramPacket(buf, buf.length);
            try {
                System.out.println("Waiting for message...");
                socket.receive(message);
                receiveQueue.put(message);

            } catch (IOException e) {
                System.err.println("Error receiving packet in Receiver");
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.err.println("Error putting message on queue in Receiver");
                e.printStackTrace();
            }
        }

    }


}