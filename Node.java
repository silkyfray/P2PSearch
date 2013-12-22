import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Node implements PeerSearchSimplified {
    private Sender sender;
    private Controller controller;
    private Receiver receiver;
    @Override
    public void init(DatagramSocket udp_socket) {
        BlockingQueue<DatagramPacket> sendQueue = new ArrayBlockingQueue(512);
        BlockingQueue<DatagramPacket> receiveQueue = new ArrayBlockingQueue(512);
        sender = new Sender(sendQueue);
        controller = new Controller(receiveQueue,sendQueue);
        receiver = new Receiver(udp_socket,receiveQueue);
        Thread t1 = new Thread(sender);
        Thread t2 = new Thread(receiver);
        Thread t3 = new Thread(controller);
        t1.start();
        t2.start();
        t3.start();
    }

    @Override
    public long joinNetwork(InetSocketAddress bootstrap_node, String identifier, String target_identifier) {
        System.out.println(identifier + " is joining the network through " + target_identifier);
        int nodeHash = NetworkProperties.hashCode(identifier);
        int bootHash = NetworkProperties.hashCode(target_identifier);
        controller.sendJoin(bootstrap_node,nodeHash,bootHash);
        return nodeHash;
    }

    @Override
    public boolean leaveNetwork(long network_id) {
    	controller.leaveNetwork(network_id);
        return true;
    }

    @Override
    public void indexPage(String url, String[] unique_words) {
//    	   node13.indexPage("http://somepage.com", ["abba"]); // this message would route to abba node
    	controller.sendIndex(url,unique_words);
    }

/*
 	as a user, I type in my keywords " john, trinity, baseball" on the user level. 
 	I pass those set of keywords to the controller. He gives me back an array of SearchResult containing: 
	class SearchResult{
    	String[] words; // strings matched for this url
    	String url;   // url matching search query
    	long frequency; //number of hits for page
	}
	Interpret a SearchResult as representing a single url. 
	Containing which of the keywords in words[] matched and a total sum of the hits for the keywords on that url.
*/
    public SearchResult[] search(String[] words) {
    	
// 	   SearchResults results = node13.search(["boyzone"]); // this message would route to boyzone node
        Vector<SearchResponse> responses = controller.sendSearch(words);
        if(responses.isEmpty())return (new SearchResult[0]);
        // A big constraint: a node cannot be waiting for more than one search. Otherwise it is hard to categorise
        // the search responses. It can still receive index requests and the others while its waiting though.
        ArrayList<SearchResult> results = new ArrayList<SearchResult>();
        //reassemble responses to results
        ArrayList<ResultPart> intermediaryResults = new ArrayList<Node.ResultPart>();
        for(SearchResponse response: responses){
        	for(Url url:response.urls){
        		intermediaryResults.add(new ResultPart(response.word, url.url, url.rank));//makes my life easier converting
        	}
        }
        Collections.sort(intermediaryResults);
        String curr = intermediaryResults.get(0).url;

        //the following loop partitions the urls. It is similar to a functional language takeWhile
        // in this case it builds up a SearchResult as long as the next url is the same as the current.
        //its a bit of hack but it is hard to do comparisons on static arrays such as the words[] in SearchResult
        //while it is building. I cannot check search_results.get(current_url).words.contains(word)
        //which would have made my life easier
        ArrayList<String> urlWords = new ArrayList<String>();
        int urlFrequency = 0;
        SearchResult newResult = new SearchResult();
        for(int i = 0; i < intermediaryResults.size(); i++) {
        	ResultPart next = intermediaryResults.get(i);
			if(next.url.equals(curr)){
				urlFrequency += next.rank;
				urlWords.add(next.word);
			}else{				
				newResult.url = curr;
				newResult.words = (String[])urlWords.toArray();
				newResult.frequency = urlFrequency;
				results.add(newResult);
				
				curr = next.url;
				urlFrequency = next.rank;
				urlWords.clear();
				urlWords.add(next.word);
				newResult = new SearchResult();
			}
		}
        //the cleanup after the last iteration of the loop
        newResult.words = (String[])urlWords.toArray();
        newResult.frequency = urlFrequency;
        results.add(newResult);
        
        return (SearchResult[])results.toArray();
      //Stick to the interface, brother. Stick to the interface.
    }
    private class ResultPart implements Comparable<ResultPart>{
    	String word,url;
    	int rank;
    	ResultPart(String w, String u, int r){
    		word = w; url = u; rank = r;
    	}
		//we want to sort them based on url so that the same urls in an array align next to each other
		public int compareTo(ResultPart obj) {
			return url.compareTo(obj.url);
		}
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof ResultPart){
				return (url == ((ResultPart)obj).url);
			}
			return super.equals(obj);
		}
		
    }

}
