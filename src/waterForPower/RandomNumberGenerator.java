/**
 * This class generates random numbers to select a random sample of cities (rows)
 * from the database.
 * 
 * @author Halil Murat
 */

package waterForPower;

import java.util.HashSet;

public class RandomNumberGenerator 
{
	private static final int SAMPLE_SIZE = 100;
	
	// Do not forget to update this value if the data set has a different number of cities
	// 19899 is the number of cities we have in the data set when the cities with 0 water are removed.
	private static final int NUMBER_OF_CITIES = 19899;
	private static HashSet<Integer> generated = new HashSet<>(SAMPLE_SIZE);
	
	public static void main(String[] args) 
	{
		int index;
		for (int i = 0; i < SAMPLE_SIZE; i++)
		{
			index = (int) (Math.random() * NUMBER_OF_CITIES + 1);
			if (generated.contains(index))
			{
				i = i - 1;
				continue;
			}
			generated.add(index);
			System.out.println(index);
		}
	}
}