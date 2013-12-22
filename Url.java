
public class Url implements Comparable<Url> {
	  public String url; 
	  public int rank;
	  public Url(String url, int rank) { 
		  this.url = url;
		  this.rank = rank;
	  }
	@Override
	//if urls links match then equal else based on rank
	public int compareTo(Url o) {
		if(url.equals(o.url)) return 0;
		else if(rank <= o.rank) return -1;
		return 1;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Url){
			return (url.equals(((Url) obj).url));
		}
		return super.equals(obj);
	}
	
}