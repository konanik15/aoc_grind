package year2025.day5;

import year2025.Year2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day5 extends Year2025 {

    public static void main(String[] args) throws IOException {
        String contentTest = Files.readString(Path.of(PATH + "day5/test.txt"));
        String contentInput = Files.readString(Path.of(PATH + "day5/input.txt"));

        System.out.println(resolve1Star(contentTest));
        System.out.println(resolve1Star(contentInput));

        System.out.println(resolve2Star(contentTest));
        System.out.println(resolve2Star(contentInput));
    }

    record Range(long start, long end) {
    }

    private static int resolve1Star(String contentTest) {
        contentTest = contentTest.replaceAll("\\r", "");
        String[] parts = contentTest.split("\n\n");
        List<String> freshRanges = Arrays.asList(parts[0].split("\n"));
        List<Range> sortedRanges = new java.util.ArrayList<>(freshRanges.stream()
                .map(line -> new Range(Long.parseLong(line.split("-")[0]),
                        Long.parseLong(line.split("-")[1])))
                .sorted(Comparator.comparing(Range::start).thenComparing(Range::end))
                .toList());

        for (int i = 0; i + 1 < sortedRanges.size(); i++) {
            while (i + 1 < sortedRanges.size() &&
                    sortedRanges.get(i).end >= sortedRanges.get(i + 1).start - 1) {
                var current = sortedRanges.remove(i);
                var next = sortedRanges.get(i);
                sortedRanges.set(i, new Range(current.start, Math.max(current.end, next.end)));
            }
        }

        var availableIngredients = Arrays.stream(parts[1].split("\n")).map(Long::parseLong).toList();
        var sum = 0;

        for (Long availableIngredient : availableIngredients) {
            if (includedInRanges(availableIngredient, sortedRanges)) {
                sum++;
            }
        }


        return sum;
    }

    private static long resolve2Star(String contentTest) {
        contentTest = contentTest.replaceAll("\\r", "");
        String[] parts = contentTest.split("\n\n");
        List<String> freshRanges = Arrays.asList(parts[0].split("\n"));
        List<Range> sortedRanges = new java.util.ArrayList<>(freshRanges.stream()
                .map(line -> new Range(Long.parseLong(line.split("-")[0]), Long.parseLong(line.split("-")[1])))
                .sorted(Comparator.comparing(Range::start).thenComparing(Range::end))
                .toList());

        for (int i = 0; i + 1 < sortedRanges.size(); i++) {
            while (i + 1 < sortedRanges.size() &&
                    sortedRanges.get(i).end >= sortedRanges.get(i + 1).start - 1) {
                var current = sortedRanges.remove(i);
                var next = sortedRanges.get(i);
                sortedRanges.set(i, new Range(current.start, Math.max(current.end, next.end)));
            }
        }

        long sum = 0;

        for (Range range : sortedRanges) {
            sum += range.end - range.start + 1;
        }

        return sum;
    }

    private static boolean includedInRanges(Long aLong, List<Range> sortedRanges) {
        for (Range range : sortedRanges) {
            if (aLong >= range.start && aLong <= range.end) return true;
            else if (range.start > aLong) return false;
        }
        return false;
    }


}