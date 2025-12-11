package year2025.day3;

import year2025.Year2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Day3 extends Year2025 {

    public static void main(String[] args) throws IOException {
        String contentTest = Files.readString(Path.of(PATH + "day3/test.txt"));
        String contentInput = Files.readString(Path.of(PATH + "day3/input.txt"));

        // System.out.println(resolve1Star(contentTest));
        // System.out.println(resolve1Star(contentInput));

        // System.out.println(resolve2Star(contentTest));
        System.out.println(resolve2Star(contentInput));
    }

    private static long resolve2Star(String contentTest) {
        var content = Arrays.stream(contentTest.replaceAll("\\r", "").split("\n")).toList();
        long sum = 0;
        for (String line : content) {
            Deque<Character> stack = new ArrayDeque<>();
            var deletionLefts = line.length() - 12;
            for (char c : line.toCharArray()) {
                var value = Character.getNumericValue(c);
                while (deletionLefts > 0 && !stack.isEmpty() && value > Character.getNumericValue(stack.peekLast())) {
                    stack.removeLast();
                    deletionLefts--;
                }
                stack.addLast(c);  // dodaj na koniec
            }

            while(stack.size() > 12) {
                stack.removeLast();
            }

            StringBuilder sb = new StringBuilder();
            for (char c : stack) {
                sb.append(c);
            }
            long liczba = Long.parseLong(sb.toString());
            sum += liczba;

            System.out.println(stack);

        }
        return sum;
    }


    private static int resolve1Star(String contentTest) {
        var content = Arrays.stream(contentTest.replaceAll("\\r", "").split("\n")).toList();
        var sum = 0;
        for (String line : content) {
            int biggestFirstPosition = Integer.parseInt((line.substring(line.length() - 2, line.length() - 1)));
            int biggestSecondPosition = Integer.parseInt(line.substring(line.length() - 1));
            var reversedLine = new StringBuffer(line).reverse().substring(2);
            for (char c : reversedLine.toCharArray()) {
                var value = Character.getNumericValue(c);
                if (value >= biggestFirstPosition) {
                    if (biggestSecondPosition < biggestFirstPosition) {
                        biggestSecondPosition = biggestFirstPosition;
                    }
                    biggestFirstPosition = value;
                }
            }
            System.out.println("BiggestPosition for " + line + " was " + biggestFirstPosition +
                    biggestSecondPosition);

            sum += biggestFirstPosition * 10 + biggestSecondPosition;

        }
        return sum;
    }

}