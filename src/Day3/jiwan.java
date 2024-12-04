package Day3;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class jiwan {

    private static final String FILE_PATH = "src/Day3/input.txt";
    private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    // looking for anything that has mul(x,y) where x,y are 1-3 digit numbers
    private static final Pattern DO_PATTERN = Pattern.compile("do()");
    private static final Pattern DONT_PATTERN = Pattern.compile("don't()");
    // potential issue this way is that every time we find "don't" we'll also find "do"

    @SneakyThrows
    public static void main(String[] args) {
        Path filePath = Paths.get(FILE_PATH);
        String input = Files.readString(filePath);

        Matcher mulMatch = MUL_PATTERN.matcher(input);
        Matcher doMatch = DO_PATTERN.matcher(input);
        Matcher dontMatch = DONT_PATTERN.matcher(input);

        boolean enabled = true;
        int matchCount = 0;
        int addedMatches = 0;

        int index = 0;
        while (index < input.length()) {
            boolean mulFound = mulMatch.find(index);
            boolean dontFound = dontMatch.find(index);
            boolean doFound = doMatch.find(index);

            if (!mulFound && !dontFound) {
                break;
            }

            if (dontFound && (dontMatch.start() < mulMatch.start())) {
                enabled = false;
                index = dontMatch.end();
            } else if (doFound && (doMatch.start() < mulMatch.start())) {
                enabled = true;
                index = doMatch.end();
            }
            else {
                if (enabled) {
                    String mulStatement = mulMatch.group();
                    System.out.println("Mul statement to be added: " + mulStatement);
                    int xyMultiplied = getXyMultiplied(mulStatement);
                    addedMatches += xyMultiplied;
                }
                matchCount++;
                index = mulMatch.end();
            }
        }

        int output = addedMatches;
        System.out.println("Match count: " + matchCount);
        System.out.println("Output: " + output);
    }

    private static int getXyMultiplied(String mulStatement) {
        // Grab x and y from the matches to parse as ints
        int startIndex = mulStatement.indexOf('(');
        int endIndex = mulStatement.indexOf(')');
        String xyString = mulStatement.substring(startIndex + 1, endIndex);
        String[] parts = xyString.split(",");

        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);

        return x * y;
    }
}
