package Day3;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String PART1_FILE_PATH = "src/Day3/input.txt";
    private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    // looking for anything that has mul(x,y) where x,y are 1-3 digit numbers
    private static final Pattern DO_PATTERN = Pattern.compile("do");
    private static final Pattern DONT_PATTERN = Pattern.compile("don't");
    // potential issue this way is that every time we find "don't" we'll also find "do"
    // so need to make sure we find "do" first in this case

    @SneakyThrows
    public static void main(String[] args) {
        Path filePath = Paths.get(PART1_FILE_PATH);
        String input = Files.readString(filePath);
        Matcher mulMatcher = MUL_PATTERN.matcher(input);
        int matchCount = 0;
        int addedMatches = 0;

        boolean enabled = true;
        Matcher doMatcher = DO_PATTERN.matcher(input);
        Matcher dontMatcher = DONT_PATTERN.matcher(input);

        int index = 0;
        while (index < input.length()) {
            if (doMatcher.find(index)) {
                System.out.println("Do found, enabling...");
                enabled = true;
                index = doMatcher.end();
            } else if (dontMatcher.find(index)) {
                System.out.println("Don't found, disabling...");
                enabled = false;
                index = dontMatcher.end();
            }

            if (mulMatcher.find(index)) {
                String mulStatement = mulMatcher.group();
                if (enabled) {
                    int xyMultiplied = getXyMultiplied(mulStatement);
                    addedMatches += xyMultiplied;
                }
                matchCount++;
                index = mulMatcher.end();
            } else {
                break;
            }
        }

        System.out.println("Match count: " + matchCount);
        System.out.println("Output: " + addedMatches);
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
