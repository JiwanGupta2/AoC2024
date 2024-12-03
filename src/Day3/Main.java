package Day3;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String PART1_FILE_PATH = "src/Day3/input.txt";
    private static final Pattern MUL_MATCHER = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

    @SneakyThrows
    public static void main(String[] args) {

        Path filePath = Paths.get(PART1_FILE_PATH);

        String input = Files.readString(filePath);
        Matcher mulmatcher = MUL_MATCHER.matcher(input);
        int matchCount = 0;
        int addedMatches = 0;

        while (mulmatcher.find()) {
            String mulStatement = mulmatcher.group();
            int xyMultiplied = getXyMultiplied(mulStatement);
            addedMatches = addedMatches + xyMultiplied;
            matchCount++;
        }

        int output = addedMatches;
        System.out.println("Match count :" + matchCount);
        System.out.println("Output :" + output);
    }

    private static int getXyMultiplied(String mulStatement) {
        // Grab x and y from the matches to parse as ints
        int startIndex = mulStatement.indexOf('(');
        int endIndex = mulStatement.indexOf(')');
        String xyString = mulStatement.substring(startIndex + 1, endIndex);
        String[] parts = xyString.split(",");

        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);

        return x*y;
    }
}
