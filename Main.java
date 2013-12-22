import java.net.*;
import java.util.Random;
/** CS4032 Distributed Systems Project 2013, Trinity College Dublin
 * Created by tony on 08/12/13.
 * This program licences under the MIT license
 * You can see a copy of the license here http://opensource.org/licenses/MIT
 *
 * @author Antonio Nikolov
 */
public class Main {
    public static void main(String [] args){
        if(args.length == 0){
            System.out.println("Starting simulation.");
            initialiseSimulation();
        }else if(args[0].equals("--boot"))
            initialiseAsJoining(args);
        else if(args[0].equals("--bootstrap"))
            initialiseAsBootstrap(args);
        else{
            System.out.println("Please specify --boot <numeric_id> or --bootstrap <ip> <numeric_id>");
        }
    }
    private static void initialiseAsJoining(String [] args){
        try {
            int port = NetworkProperties.nPort;
            NetworkProperties.nAddress = InetAddress.getLocalHost().getHostAddress();  //should give local ip
            InetSocketAddress bootstrapAddress = new InetSocketAddress(args[1],port);
            DatagramSocket socket = new DatagramSocket(port);
            PeerSearchSimplified node1 = new Node();
            node1.init(socket);
            node1.joinNetwork(bootstrapAddress,"myname",args[2]);//i.e bootstrap node

        } catch (SocketException e) {
            System.err.println("Could not open socket.");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }
    private static void initialiseAsBootstrap(String [] args) {
        try {
            int port = NetworkProperties.nPort;
            NetworkProperties.nAddress = InetAddress.getLocalHost().getHostAddress();  //should give local ip
            InetSocketAddress bootstrapAddress = new InetSocketAddress("127.0.0.1",port);
            DatagramSocket socket = new DatagramSocket(port);
            PeerSearchSimplified node1 = new Node();
            node1.init(socket);
            node1.joinNetwork(bootstrapAddress,"myname","myname");//i.e bootstrap node. won' try to connect

        } catch (SocketException e) {
            System.err.println("Could not open socket.");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static void initialiseSimulation(){
        int port = NetworkProperties.nPort;
        String address = NetworkProperties.nAddress;
        InetSocketAddress bootstrapAddress = new InetSocketAddress(address,port);
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.err.println("Could not open socket.");
            e.printStackTrace();
        }
        final String [] names = {
                "anthony","carmela","tony jnr","meadow","silvio","pauli","corrado","chris"
        };
        PeerSearchSimplified [] nodes = new PeerSearchSimplified[names.length];
        System.out.println("Initializing crime fa... nodes");
        for(int i = 0; i < nodes.length; ++i){
            nodes[i] = new Node();
            nodes[i].init(socket);
        }
        nodes[0].joinNetwork(bootstrapAddress,names[0],names[0]);//i.e bootstrap node
        nodes[1].joinNetwork(bootstrapAddress,names[1],names[0]);
        nodes[2].joinNetwork(bootstrapAddress,names[2],names[1]);
        nodes[3].joinNetwork(bootstrapAddress,names[3],names[1]);


        Random rand = new Random();
        for(int i = 4; i < nodes.length; ++i){
            int boot_random = rand.nextInt(3);
            //System.out.println(names[i] + " is joining through " + names[boot_random]);
            nodes[i].joinNetwork(bootstrapAddress,names[i],names[boot_random]);
        }

        nodes[0].indexPage("google.com",new String[]{"dog","car","hello","CS4032"});
        nodes[1].indexPage("santaisevil.com",new String[]{"gnome,elf,","somewhere"});
        nodes[2].indexPage("google.com",new String[]{"train","station","is","here"});
        SearchResult[] results = nodes[0].search(new String[]{"hello","dog","gnome"});
        System.out.println("Printing results =>");
        for (int i = 0; i < results.length; ++i){
            System.out.print("node: " + i + " url: "  + results[i].url + " frequency: " + results[i].frequency + " words: ");
            for (int j = 0; j < results[i].words.length; ++j){
                System.out.print(", " + results[i].words[j]);
            }
        }
    }



// ====== how to use json simple
//        JSONObject obj=new JSONObject();
//        obj.put("type",MessageType.ROUTING_INFO.name());
//        obj.put("gateway_id","34");
//        obj.put("node_id","42");
//
//
//        JSONObject ri0 = new JSONObject();
//        ri0.put("node_id", "3");
//        ri0.put("ip_address", "127.0.0.1");
//        JSONObject ri1 = new JSONObject();
//        ri1.put("node_id", "22");
//        ri1.put("ip_address", "127.0.0.2");
//
//        JSONArray route_table = new JSONArray();
//        route_table.add(ri0);
//        route_table.add(ri1);
//        obj.put("route_table",route_table);
//
//        StringWriter out = new StringWriter();
//        obj.writeJSONString(out);
//        String jsonText = out.toString();
//        System.out.print(jsonText);




}
