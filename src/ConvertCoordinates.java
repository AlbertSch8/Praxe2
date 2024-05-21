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

    static String processLine(String line) {
        String[] columns = line.split(",");

        if (columns.length >= 7) {
            // Check if the seventh column contains coordinates in DMS format
            columns[6] = convertLineCoordinates(columns[6]);
        }

        return String.join(",", columns);
    }

    static String convertLineCoordinates(String coordinate) {
        // Regex pattern for finding coordinates in the format of DMS
        String regexPattern = "\"(\\d+)°(\\d+)'(\\d+.?\\d*)\"\"([NS])\\s(\\d+)°(\\d+)'(\\d+.?\\d*)\"\"([WE])";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(coordinate);

        // Check if coordinates are found in the string
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

            // Adjust latitude and longitude based on direction
            if (latitudeDirection.equals("S")) {
                latitude = -latitude;
            }
            if (longitudeDirection.equals("W")) {
                longitude = -longitude;
            }

            // Further divide by 60
            //latitude /= 60;
            //longitude /= 60;

            // Format the coordinates in decimal degrees
            String formattedLatitude = formatCoordinate(latitude);
            String formattedLongitude = formatCoordinate(longitude);

            return formattedLatitude + ", " + formattedLongitude;
        } else {
            // If coordinates are not found, return the original string
            return coordinate;
        }
    }

    private static String formatCoordinate(double coordinate) {
        // Format the coordinate to have 6 decimal places
        return String.format("%.6f", coordinate);
    }
}
