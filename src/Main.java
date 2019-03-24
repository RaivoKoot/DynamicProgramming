public class Main {

	public static void printArray(int[][] array) {
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
				int element = array[j][i];
				if (element < 10)
					System.out.print("0");
				System.out.print(array[j][i]);
				System.out.print(" | ");
			}
			System.out.print("]\n");
		}
	}

	public static int[][] bottomUpAchievableLength(int[] vids, int maximum_allowed_length) {
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
		int[][] achievable_lengths = new int[MAX_LENGTH][NUM_VIDEOS];
		
		/* iterate over every field */
		for (int max_time = 1; max_time < MAX_LENGTH; max_time++) {

			// num_allowed_videos = 1 means the videos[0...1] are allowed. So for 1, the
			// dummy video and the first actual video is allowed.
			for (int num_allowed_videos = 1; num_allowed_videos < NUM_VIDEOS; num_allowed_videos++) {
				
				// apply bellman equation
				int max_found_time = findLongestPlaylist(num_allowed_videos, max_time, achievable_lengths,
						videos);

				achievable_lengths[max_time][num_allowed_videos] = max_found_time;
			}
		}

		// achievable_lengths now contains all the resulting information
		return achievable_lengths;
	}

	public static void main(String[] args) {
		int[] videos = { 4, 3, 2, 7, 2 };
		int max_time = 40;

		printArray(bottomUpAchievableLength(videos, max_time));

	}

	public static int findLongestPlaylist(int allowed_videos, int max_time, int[][] achievable_lengths,
			int[] videos) {

		int new_video_length = videos[allowed_videos];
		if (new_video_length == max_time)
			return new_video_length;

		int entry_above = achievable_lengths[max_time][allowed_videos - 1];

		// check if entry above already equals max_length
		if (entry_above == max_time)
			return entry_above;

		// iterate over row above from right to left
		for (int selected_time = max_time - 1; selected_time >= 0; selected_time--) {
			int achieved_length = new_video_length + achievable_lengths[selected_time][allowed_videos - 1];

			if (achieved_length <= max_time && achieved_length > entry_above)
				return achieved_length;

		}

		// return value from entry above
		return entry_above;
	}

}
