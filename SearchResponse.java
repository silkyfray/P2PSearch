import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

// a single word, many urls and each url has a rank
public class SearchResponse {
	String word;
	ArrayList<Url> urls; // Java, where me tuples, bro!?

	public static SearchResponse fromResponseJson(JSONObject json) {
		SearchResponse response = new SearchResponse();
		response.word =  (String)json.get("word");
		JSONArray urls = (JSONArray)json.get("response");
		for(Object row: urls){
			JSONObject url = (JSONObject) row;
			String name = (String)url.get("url");
			int rank = Integer.valueOf((String)url.get("rank"));
			response.urls.add(new Url(name,rank));
		}
		return response;
	}
	public static SearchResponse fromIndexJson(JSONObject json) {
		SearchResponse index = new SearchResponse();
		index.word =  (String)json.get("keyword");
		JSONArray links = (JSONArray)json.get("link");
		for (int i = 0; i < links.size(); i++) {
			index.urls.add(new Url((String)links.get(i),1));
		}
		return index;
	}
	@SuppressWarnings("unchecked")
	public JSONObject toJson() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("word", word);
		JSONArray jsonUrls = new JSONArray();
		for(Url url: urls){
			JSONObject curr = new JSONObject();
			curr.put("url", url.url);
			curr.put("rank", String.valueOf(url.rank));
			jsonUrls.add(curr);
		}
		jsonObj.put("response", jsonUrls);
		return jsonObj;
	}

}
