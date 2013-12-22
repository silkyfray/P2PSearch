/**
 * Created by tony on 18/12/13.
 */
//*todo* incorporate popularity
public class Contact {
    private long timestamp;
    private String ip;
    private final int id;
    public Contact(int nodeID, String nodeIP){
        ip = nodeIP; if(ip == null)ip = "127.0.0.1";// a quick hack there is a problem assigning ip somewhere
        id = nodeID;
        timestamp = System.currentTimeMillis();
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }


}
