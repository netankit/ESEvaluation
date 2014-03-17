import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class generates a CSV file consisting of the average values of execution
 * time.
 * 
 * @author ankit
 * 
 */
public class ESGraphAverage {

	public static void main(String[] args) {
		if (args.length != 4) {
			System.out
					.println("USAGE:ESGraph <inputFile> <outputfile_csv> <first_segment_size> <second_segment_size>");
		} else {

			String inputFile = args[0];
			String outputfile_csv = args[1];
			int first_segment_size = Integer.parseInt(args[2]);
			int second_segment_size = Integer.parseInt(args[3]);

			try {
				System.out.println("Execution Started.........");
				// Open the file that is the first
				// command line parameter
				FileInputStream fstream = new FileInputStream(inputFile);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				// Writes to the file
				FileWriter foutstream = new FileWriter(outputfile_csv);
				BufferedWriter out = new BufferedWriter(foutstream);

				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));

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
						// totalSum = totalSum +
						// Integer.parseInt(lineFragment[6]);
						if (lineNumber % first_segment_size == 0) {
							// System.out.println("i am here too");
							averageval = calculateAverage(totalSum,
									first_segment_size);
							myTwenty.add(averageval);
							totalSum = 0;
							averageval = 0;
						}
						lineNumber++;
					} else
						continue;
				}
				System.out.println("Total Input Values: " + lineNumber);
				System.out
						.println("Total Input Values after First Segmentation: "
								+ myTwenty.size());
				System.out.println("First Segment Populated");

				long arrayCount = 1;
				long totalSumChunk = 0;
				long averagevalchunk;

				// Finding average for every 50 values in the arraylist
				for (long i = 0; i < myTwenty.size(); i++) {
					totalSumChunk += myTwenty.get((int) i);
					if (arrayCount % second_segment_size == 0) {
						// System.out.println("i am here too");
						averagevalchunk = calculateAverage(totalSumChunk,
								second_segment_size);
						myTwoThousand.add(averagevalchunk);
						totalSumChunk = 0;
						averagevalchunk = 0;
					}
					arrayCount++;

				}
				// System.out.println(myTwoThousand.get(1));
				// System.out.println(myTwoThousand.get(5));
				System.out
						.println("Number of Values in Second Segment/ Total Output Values : "
								+ myTwoThousand.size());
				System.out.println("Second Segment Populated");

				String outputTextAvg;
				for (long chunkCountTemp = 0; chunkCountTemp < myTwoThousand
						.size(); chunkCountTemp++) {
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
	}

	private static long calculateAverage(long totalSum, int size) {
		long avg;
		avg = totalSum / size;
		return avg;
	}
}
