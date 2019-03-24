import java.util.TreeSet;

public class Playlist {
	private int length;
	private TreeSet<Integer> video_indices;

	// createe empty playlist
	public Playlist() {
		this.length = 0;
		this.video_indices = new TreeSet<Integer>();
	}

	// create a playlist with one video in it
	public Playlist(int video_index, int video_length) {
		this.length = video_length;
		this.video_indices = new TreeSet<Integer>();
		this.addVideo(video_index);
	}

	// create a playlist from a playlist and one more video
	public Playlist(Playlist playlist, int new_video_index, int new_length) {
		this.setVideo_indices((TreeSet<Integer>) (playlist.getVideo_indices().clone()));
		this.addVideo(new_video_index);
		this.length = new_length;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public TreeSet<Integer> getVideo_indices() {
		return video_indices;
	}

	public void setVideo_indices(TreeSet<Integer> video_indices) {
		this.video_indices = video_indices;
	}

	public void addVideo(int new_video_index) {
		video_indices.add(new_video_index);
	}
}
