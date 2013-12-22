/**
 * Created by tony on 15/12/13.
 */
public class NetworkProperties {
    public static final int MTU = 1500;
    public static final int nPort = 8767;
    public static String nAddress = "127.0.0.1"; // default. static because for now all nodes are on the same machine
    public static int refreshTime = 120; // global refresh rate of the routing table in seconds
    public static int staleTime = 180; // the maximum time a node will stay in routing table not updated before it is contacted
    public static int responseWait = 3; // the time before a PING is fired after a SEARCH
    public static int pingWait = 30; // the time before node is removed from routing table after PING
    

    public static int hashCode(String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = hash * 31 + str.charAt(i);
        }
        return Math.abs(hash);
    }

}
