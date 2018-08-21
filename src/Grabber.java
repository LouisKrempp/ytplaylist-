import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Grabber {

	// Variables
	// We need a real browser user agent or Google will block our request with a 403 - Forbidden
	// Agent is using old youtube css; tested with user agent Opera 12.14
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	private static final String ytURL = "https://www.youtube.com/user/";
	private String username;
	private Document doc;
	private HashMap<String, String> playlistsTitleURLHM = new HashMap();
	private HashMap<String, ArrayList<String>> videosByPlHM = new HashMap();
	private boolean success = false;
	
	public Grabber(String username) {
		try {
			this.username = username;
			AppBuilder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Document AppBuilder() throws IOException {
		try {
			if(username.isEmpty()) throw new Exception("User not referenced");
			
			String URL = ytURL + username + "/playlists?view=1&flow=grid";
			System.out.println("Youtube channel link : " + URL);
			
			// Fetch the page
			this.doc = Jsoup.connect(URL).userAgent(USER_AGENT).get();
			this.saveURL();
			success = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return this.doc;
	}

	private void saveURL() {
		// Agent is using old youtube css; tested with user agent Opera 12.14
		for (Element result : this.doc.select(".yt-lockup-title a")) {
			final String url = "https://www.youtube.com" + result.attr("href");
			final String title = result.attr("title");
			playlistsTitleURLHM.put(title, url);
		}
	}
	
	public void saveVideos(ArrayList<String> playlistsChecked) throws IOException{
		for(String s : playlistsChecked) {
			String URL = playlistsTitleURLHM.get(s);
			this.doc = Jsoup.connect(URL).userAgent(USER_AGENT).get();
			
			ArrayList<String> videoSet = new ArrayList();
			for (Element result : this.doc.select(".pl-video-title > a")) {
				final String title = result.text();
				if(!title.startsWith("[")) videoSet.add(title);
			}
			videosByPlHM.put(s, videoSet);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public HashMap<String, String> getPlaylistsTitleURLHM() {
		return playlistsTitleURLHM;
	}

	public void setPlaylistsTitleURLHM(HashMap<String, String> playlistsURLs) {
		this.playlistsTitleURLHM = playlistsURLs;
	}

	public HashMap<String, ArrayList<String>> getVideosByPlHM() {
		return videosByPlHM;
	}

	public void setVideosByPlHM(HashMap<String, ArrayList<String>> videosByPlHM) {
		this.videosByPlHM = videosByPlHM;
	}

	public boolean isSuccess() {
		return success;
	}
	
	
	
}
