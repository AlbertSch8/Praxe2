import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String inputFile = "C:\\Users\\Albert\\Desktop\\Praxe3.csv";
        String outputFile = "C:\\Users\\Albert\\Desktop\\Praxe3_converted.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Process the line and convert the coordinates
                String convertedLine = processLine(line);

                // Write the updated line to the output file
                writer.write(convertedLine + "\n");
            }

            System.out.println("Conversion completed. Output saved to: " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processLine(String line) {
        String[] columns = line.split(",");

        if (columns.length >= 2) {
            // Check if the seventh column contains coordinates in DMS format
            System.out.println("Original line: " + line);
            columns[0] = ConvertCoordinates.convertLineCoordinates(columns[0]);
            columns[1] = ConvertCoordinates.convertLineCoordinates(columns[1]);
            System.out.println("Converted line: " + String.join(",", columns));
        }

        return String.join(",", columns);
    }


}