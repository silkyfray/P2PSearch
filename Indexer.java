import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Indexer {
	private Map<String,SearchResponse> storage;
	public Indexer(){
		storage = new HashMap<String, SearchResponse>();//single word, many urls
	}
	public void index(SearchResponse index) {
		String searchWord = index.word;
		if(storage.containsKey(searchWord)){
			//for each url in the index
			for(Url url: index.urls){			
				ArrayList<Url>  storedIndex =storage.get(searchWord).urls;
				int pos = storedIndex.indexOf(url);
				if(pos >= 0){
					Url updated = storedIndex.get(pos);
					updated.rank += url.rank;
					storedIndex.set(pos, updated);
				}else{
					storedIndex.add(url);
				}
			}
		}else{
			storage.put(index.word, index);
		}		
	}
	public SearchResponse getSearchResponse(String string) {
		return storage.get(string);
	}
	
	


}
