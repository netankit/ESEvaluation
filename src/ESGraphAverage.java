import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ESGraphAverage {

	public static void main(String[] args) {

		String inputFile = "bulkTookFile.txt";
		try {
			System.out.println("Execution Started.........");
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(inputFile);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			// Writes to the file named "inputFileName_trie_out"
			FileWriter foutstream = new FileWriter("output_avg.csv");
			BufferedWriter out = new BufferedWriter(foutstream);

			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;

			String[] lineFragment;

			List<Long> myTwenty = new ArrayList<Long>();
			List<Long> myTwoThousand = new ArrayList<Long>();
			long lineNumber = 1;
			long averageval;
			long totalSum = 0;

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				if (strLine.startsWith("bulk took : ")) {
					// System.out.println("i am in");
					lineFragment = strLine.split(" ");
					totalSum = totalSum + Integer.parseInt(lineFragment[3]);
					if (lineNumber % 20 == 0) {
						// System.out.println("i am here too");
						averageval = calculateAverage(totalSum, 20);
						myTwenty.add(averageval);
						totalSum = 0;
						averageval = 0;
					}
					lineNumber++;
				} else
					continue;
			}
			System.out.println("LN: " + lineNumber);

			System.out.println(myTwenty.size());

			System.out.println("myTwoTwentyArray Populated");

			long arrayCount = 1;
			long totalSumChunk = 0;
			long averagevalchunk;

			// Finding average for every 2000 values in the arraylist
			for (long i = 0; i < myTwenty.size(); i++) {
				totalSumChunk += myTwenty.get((int) i);
				if (arrayCount % 50 == 0) {
					// System.out.println("i am here too");
					averagevalchunk = calculateAverage(totalSumChunk, 50);
					myTwoThousand.add(averagevalchunk);
					totalSumChunk = 0;
					averagevalchunk = 0;
				}
				arrayCount++;

			}
			System.out.println(myTwoThousand.get(1));
			System.out.println(myTwoThousand.get(5));

			System.out.println("myTwoThousandArray Populated");

			String outputTextAvg;
			for (long chunkCountTemp = 0; chunkCountTemp < myTwoThousand.size(); chunkCountTemp++) {
				outputTextAvg = chunkCountTemp + 1 + ","
						+ myTwoThousand.get((int) chunkCountTemp) + "\n";
				out.write(outputTextAvg);
			}
			System.out.println("Writing to File Completed");

			// Close the input stream
			in.close();
			out.close();
			System.out.println("### Execution Completed ###");
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	private static long calculateAverage(long totalSum, int size) {
		long avg;
		avg = totalSum / size;
		return avg;
	}
}
