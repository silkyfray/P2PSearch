import java.net.DatagramSocket;
import java.net.InetSocketAddress;

// for the simplified routing solution
interface PeerSearchSimplified {
    void init(DatagramSocket udp_socket); // initialise with a udp socket
    long joinNetwork(InetSocketAddress bootstrap_node,
                     String identifier,
                     String target_identifier ); //returns network_id, a locally
    // generated number to identify peer network
    boolean leaveNetwork(long network_id); // parameter is previously returned peer network identifier
    void indexPage(String url, String[] unique_words);
    SearchResult[] search(String[] words);
}