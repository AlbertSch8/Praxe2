import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String inputFile = "C:\\Users\\Albert\\Desktop\\Praxe3.csv";
        String outputFile = "C:\\Users\\Albert\\Desktop\\Praxe3_dd.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by comma
                String[] parts = line.split(",");

                // Check if the line contains the "gps" column
                if (parts.length >= 7) {
                    // Extract DMS coordinates
                    String dmsLatitude = parts[6];
                    String dmsLongitude = parts[7];

                    // Convert DMS coordinates to DD
                    String ddLatitude = convertDmsToDd(dmsLatitude);
                    String ddLongitude = convertDmsToDd(dmsLongitude);

                    // Add DD coordinates to the line
                    line += "," + ddLatitude + "," + ddLongitude;
                }

                // Write the updated line to the output file
                writer.write(line + "\n");
            }

            System.out.println("Conversion completed. Output saved to: " + outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String convertDmsToDd(String dmsCoordinate) {
        // Check if the DMS coordinate is not empty
        if (!dmsCoordinate.isEmpty()) {
            // Split the DMS coordinate by non-numeric characters
            String[] parts = dmsCoordinate.split("[^\\d\\w]+");

            // Check if there are enough parts for conversion
            if (parts.length >= 3) {
                // Parse degrees, minutes, and seconds
                double degrees = Double.parseDouble(parts[0]);
                double minutes = Double.parseDouble(parts[1]);
                double seconds = Double.parseDouble(parts[2]);

                // Calculate decimal degrees
                double decimalDegrees = degrees + (minutes / 60) + (seconds / 3600);

                // Return formatted decimal degrees
                return String.format("%.6f", decimalDegrees);
            }
        }
        // Return empty string if conversion fails
        return "";
    }
}