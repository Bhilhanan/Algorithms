import java.util.ArrayList;
import java.util.Random;

public class RandomInputGenerator {

	/*
	 * @param n number of integer values to insert into track
	 * @param max random numbers generated in range [0, max]
	 */
	public static ArrayList<Integer> genInTrack(int n, int max)
	{
		ArrayList<Integer> track = new ArrayList<Integer>();
		
		if (n > 0)
		{
			Random generator = new Random();
			for (int i = 1; i <= n; i++)
			{
				track.add(generator.nextInt(max));
			}
		}
		
		return track;
	}
}
