import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class ESGraph {

	public static void main(String[] args) {

		if (args.length != 3) {
			System.out
					.println("USAGE:ESEvaluation <inputFile> <outputfile_csv> <first_segment_size>");
		} else {
			try {
				String inputFile = args[0];
				String outputfile_csv = args[1];
				int first_segment_size = Integer.parseInt(args[2]);

				System.out.println("Execution Started.........");
				// Open the file that is the first
				// command line parameter
				FileInputStream fstream = new FileInputStream(inputFile);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				// Writes to the file named "inputFileName_trie_out"
				FileWriter foutstream = new FileWriter(outputfile_csv);

				BufferedWriter out = new BufferedWriter(foutstream);

				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));

				String strLine;
				long count = 1;
				long subcount = 1;
				long chunkcount = 1;
				String outputText;
				String[] lineFragment;
				// Read File Line By Line
				while ((strLine = br.readLine()) != null) {
					if (count % first_segment_size == 0) {
						if (subcount % first_segment_size == 0) {
							lineFragment = strLine.split(" ");
							outputText = chunkcount + "," + lineFragment[3]
									+ "\n";
							out.write(outputText);
							chunkcount++;
							subcount++;
							count++;
						} else {
							subcount++;
						}

					} else {
						count++;
					}

				}
				// Close the input stream
				in.close();
				out.close();
				System.out.println("### Execution Completed ###");
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}
	}
}
