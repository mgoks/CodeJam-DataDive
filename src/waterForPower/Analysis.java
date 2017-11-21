/**
 * CodeJam: DataDive
 * Group Foo
 * Members:
 * Dany Stefan
 * Halil Murat Goksel
 * Michel Abdelnour
 *
 * Null Hypothesis:
 * For all cities in the database, if the water-to-land ratio of city A is higher than that of city B, 
 * the mean household income in city A is higher than that of city B.
 *
 * The following program is to test this hypothesis. For this us-household-income-statistics.csv dataset is used.
 */

package waterForPower;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

public class Analysis implements Runnable 
{
	private static final int SAMPLE_SIZE = 5000;
	
	// Number of cities in the database
	// Do not forget to update this value if the data set has a different number of cities
	// 19899 is the number of cities we have in the data set when the cities with 0 water are removed.
	private static final int NUMBER_OF_CITIES = 19899;
	private static HashSet<Integer> generated = new HashSet<>(SAMPLE_SIZE);
	public static double ratioSum = 0.0;

    	public static void main() 
	{
		// Read data
        	String fileName = "./us-household-income-statistics-reduced.csv";
        	File file = new File(fileName);
        	List<List<String>> bigList = new ArrayList<>();
        	Scanner inputStream;

        	try 
		{
          		inputStream = new Scanner(file);
	            	while (inputStream.hasNext()) 
			{
                		String line = inputStream.nextLine();
                		String[] values = line.split(",");
                		// this adds the currently parsed line to the 2-dimensional string array
                		bigList.add(Arrays.asList(values));
            		}
            		inputStream.close();
	        } 
		catch (FileNotFoundException e) 
		{
            		e.printStackTrace();
        	}
	        List<List<String>> redList = new ArrayList<>();
        
		// Generate random indeces to select a random sample
        	int index;
		for (int i = 0; i < SAMPLE_SIZE; i++) 
		{
			index = (int) (Math.random() * NUMBER_OF_CITIES + 1);
			// Check if the index was already generated
			if (generated.contains(index)) 
			{
				i = i - 1;
				// If it is generated, generate a new random index until it is new
				continue;
			}
			generated.add(index);
			redList.add(bigList.get(index));
		}
        	
		// Creating a variable to count the number of trues and falses
        	double numTrue = 0, numFalse = 0;
		// Iterates through the List of Land area values
       		for (int i = 1; i < redList.size() - 1; i++) 
		{
        		// iterates through every element after the first one 
			for (int j = i + 1; j < redList.size(); j++) 
			{
        			/* we want to calculate the ratios of water to land area of a city and compare it with that of every
				other city of the samples we pick. In order to confirm out hypothesis, we check if, for most of the
				cities, for a larger water area to land area ratio we have a larger household income.
				Our analysis consists of counting how many times we get true and how many times we get false and
				then evaluating the percentage of true to false to validate our hypothesis.
				We have to keep in mind that thi analysis can go further on if we change the targeted parameter to only
				only evaluating water or land only*/
				
				double initRatio = Double.valueOf(redList.get(i).get(6)) / Double.valueOf(redList.get(i).get(5));
        			double compRatio = Double.valueOf(redList.get(j).get(6)) / Double.valueOf(redList.get(j).get(5));
        			double max = Math.max(initRatio, compRatio);
        			int maxIndex = 0;
        			if (initRatio == max) 
				{
        				maxIndex = i;
        			}
        			else 
				{
        				maxIndex = j;
        			}
        			double initMean = Double.valueOf(redList.get(maxIndex).get(7));;
        			double compMean;
        			if (maxIndex == i) 
				{
        				compMean = Double.valueOf(redList.get(j).get(7));
        			}
        			else 
				{
        				compMean = Double.valueOf(redList.get(i).get(7));
        			}
        			if (initMean > compMean) 
				{
        				numTrue++;
        			}
        			else
				{
        				numFalse++;
        			}
        		}
        	}
	        System.out.println("true/total (over 0.5 to confirm hypothesis) = " + numTrue/(numTrue + numFalse));
        	ratioSum += numTrue/(numTrue + numFalse);
    	}
	// overrides 
	@Override
	public void run() 
	{
		
		try
		{
            		Analysis.main();
	        } 
		catch (Exception e)
		{
            		// prints out any exception from Analysis
			System.out.println("An unknown exception :" + e.toString());
        	}
     	}
}