import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertCoordinates {
    public static void main(String[] args) {
        String inputFile = "C:\\Users\\Albert\\Desktop\\Praxe3.csv";
        String outputFile = "C:\\Users\\Albert\\Desktop\\Praxe3_converted.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Find coordinates using regex
                String convertedLine = convertLineCoordinates(line);

                // Write the updated line to the output file
                writer.write(convertedLine + "\n");
            }

            System.out.println("Conversion completed. Output saved to: " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertLineCoordinates(String line) {
        // Regex pattern for finding coordinates in the format of DMS
        String regexPattern = "\\b(\\d+)[°](\\d+)['](\\d+)[\"]([NS])\\s*(\\d+)[°](\\d+)['](\\d+)[\"]([EW])\\b";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(line);

        // Check if coordinates are found in the line
        if (matcher.find()) {
            // Extract coordinates from matcher groups
            double latitudeDegrees = Double.parseDouble(matcher.group(1));
            double latitudeMinutes = Double.parseDouble(matcher.group(2));
            double latitudeSeconds = Double.parseDouble(matcher.group(3));
            String latitudeDirection = matcher.group(4);
            double longitudeDegrees = Double.parseDouble(matcher.group(5));
            double longitudeMinutes = Double.parseDouble(matcher.group(6));
            double longitudeSeconds = Double.parseDouble(matcher.group(7));
            String longitudeDirection = matcher.group(8);

            // Convert coordinates to decimal degrees
            double latitude = latitudeDegrees + latitudeMinutes / 60 + latitudeSeconds / 3600;
            double longitude = longitudeDegrees + longitudeMinutes / 60 + longitudeSeconds / 3600;

            // Check direction and adjust latitude and longitude accordingly
            if (latitudeDirection.equals("S")) {
                latitude = -latitude;
            }
            if (longitudeDirection.equals("W")) {
                longitude = -longitude;
            }

            // Format the coordinates in decimal degrees
            String formattedCoordinates = String.format("%.6f, %.6f", latitude, longitude);
            // Replace DMS coordinates with DD coordinates in the line
            return line.replaceFirst(regexPattern, formattedCoordinates);
        } else {
            // If coordinates are not found, return the original line
            return line;
        }
    }
}