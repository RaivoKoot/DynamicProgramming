import java.util.TreeSet;

public class Main {

	public static void printArray(Playlist[][] array) {
		int rows = array[0].length;
		int columns = array.length;

		System.out.print("    ");
		for (int i = 0; i < columns; i++) {
			System.out.print(" ");
			if (i < 10)
				System.out.print("0");

			System.out.print(i + "  ");
		}

		System.out.println();

		for (int i = 0; i < rows; i++) {
			if (i < 10)
				System.out.print("0");
			System.out.print(i + " [ ");
			for (int j = 0; j < columns; j++) {
				int element = array[j][i].getLength();
				if (element < 10)
					System.out.print("0");
				System.out.print(array[j][i].getLength());
				System.out.print(" | ");
			}
			System.out.print("]\n");
		}
	}

	public static Playlist[][] bottomUpAchievableLength(int[] vids, int maximum_allowed_length) {
		/* Initialization */

		// copy array of video lengths to add a zero at the start
		int[] videos = new int[vids.length + 1];
		videos[0] = 0; // add dummy video with length zero

		// copy over elements
		for (int i = 0; i < vids.length; i++)
			videos[i + 1] = vids[i];

		final int NUM_VIDEOS = videos.length;
		final int MAX_LENGTH = maximum_allowed_length + 1; // add dummy zero maximum length

		// t times k table that stores all the achievable lengths
		Playlist[][] solution_table = new Playlist[MAX_LENGTH][NUM_VIDEOS];
		
		Playlist dummy = new Playlist();
		
		// first row
		for(int i = 0; i < MAX_LENGTH; i++)
			solution_table[i][0] = dummy;
		
		// first column
		for(int i = 0; i < NUM_VIDEOS; i++)
			solution_table[0][i] = dummy;

		/* iterate over every field */
		for (int max_time = 1; max_time < MAX_LENGTH; max_time++) {

			// num_allowed_videos = 1 means the videos[0...1] are allowed. So for 1, the
			// dummy video and the first actual video is allowed.
			for (int num_allowed_videos = 1; num_allowed_videos < NUM_VIDEOS; num_allowed_videos++) {

				// apply bellman equation
				Playlist max_found_time = findLongestPlaylist(num_allowed_videos, max_time, solution_table, videos);

				solution_table[max_time][num_allowed_videos] = max_found_time;
			}
		}

		// achievable_lengths now contains all the resulting information
		return solution_table;
	}

	public static void main(String[] args) {
		int[] videos = { 4, 3, 2, 7, 2 };
		int max_time = 40;
		Playlist[][] solutions = bottomUpAchievableLength(videos, max_time);
		printArray(solutions);
		
		System.out.println(solutions[10][4].getLength());
		System.out.println(solutions[10][4].getVideo_indices());
		

	}

	public static Playlist findLongestPlaylist(int allowed_videos, int max_time, Playlist[][] achievable_lengths,
			int[] videos) {

		int new_video_length = videos[allowed_videos];
		if (new_video_length == max_time)
			return new Playlist(allowed_videos, new_video_length);

		Playlist entry_above = achievable_lengths[max_time][allowed_videos - 1];

		// check if entry above already equals max_length
		if (entry_above.getLength() == max_time)
			return entry_above;

		// iterate over row above from right to left
		for (int selected_time = max_time - 1; selected_time >= 0; selected_time--) {
			Playlist shorter_playlist = achievable_lengths[selected_time][allowed_videos - 1];
			int achieved_length = new_video_length + shorter_playlist.getLength();

			if (achieved_length <= max_time && achieved_length > entry_above.getLength()) {
				Playlist solution = new Playlist(shorter_playlist, allowed_videos, achieved_length);

				return solution;
			}

		}

		// return value from entry above
		return entry_above;
	}

}
