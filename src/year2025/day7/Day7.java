package year2025.day7;

import year2025.Year2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day7 extends Year2025 {

    public static void main(String[] args) throws IOException {
        String contentTest = Files.readString(Path.of(PATH + "day7/test.txt"));
        String contentInput = Files.readString(Path.of(PATH + "day7/input.txt"));

        System.out.println(resolve1Star(contentTest));
        System.out.println(resolve1Star(contentInput));

        System.out.println(resolve2Star(contentTest));
        System.out.println(resolve2Star(contentInput));
    }

    private static int resolve1Star(String contentTest) {
        contentTest = contentTest.replaceAll("\\r", "");
        String[] parts = contentTest.split("\n");
        parts[0] = parts[0].replace('S', '|');

        Character[][] map = Arrays.stream(parts)
                .map(s -> s.chars()
                        .mapToObj(c -> (char) c)
                        .toArray(Character[]::new))
                .toArray(Character[][]::new);

        var splits = 0;

        for (int y = 0; y < map.length - 1; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '|') {
                    if (map[y + 1][x] == '^') {
                        splits++;
                        if (x > 1) map[y + 1][x - 1] = '|';
                        if (x < map[x].length - 1) map[y + 1][x + 1] = '|';
                    } else {
                        map[y + 1][x] = '|';
                    }
                }
            }
        }
        return splits;
    }


    private static long resolve2Star(String contentTest) {
        contentTest = contentTest.replaceAll("\\r", "");
        String[] parts = contentTest.split("\n");
        parts[0] = parts[0].replace('S', '|');

        Character[][] map = Arrays.stream(parts)
                .map(s -> s.chars()
                        .mapToObj(c -> (char) c)
                        .toArray(Character[]::new))
                .toArray(Character[][]::new);

        var mapWithTimelines = new Long[parts.length][parts[0].length()];

        for (int y = 0; y < parts.length; y++) {
            for (int x = 0; x < parts[0].length(); x++) {
                if (map[y][x] == '|') {
                    mapWithTimelines[y][x] = 1L;
                } else if (map[y][x] == '^') {
                    mapWithTimelines[y][x] = -1L;
                } else {
                    mapWithTimelines[y][x] = 0L;
                }
            }
        }


        for (int y = 0; y < map.length - 1; y++) {
            for (int x = 0; x < map[y].length; x++) {

                var currentTimelineValue = mapWithTimelines[y][x];

                if (currentTimelineValue >= 1) {
                    if (mapWithTimelines[y + 1][x] == -1) {
                        if (x > 0) {
                            mapWithTimelines[y + 1][x - 1] += currentTimelineValue;
                        }
                        if (x < mapWithTimelines[y].length - 1) {
                            mapWithTimelines[y + 1][x + 1] += currentTimelineValue;
                        }
                    } else {
                        mapWithTimelines[y + 1][x] += currentTimelineValue;
                    }
                }
            }
        }

        long paths = 0;
        int lastRow = mapWithTimelines.length - 1;
        for (int x = 0; x < mapWithTimelines[lastRow].length; x++) {
            if (mapWithTimelines[lastRow][x] > 0) {
                paths += mapWithTimelines[lastRow][x];
            }
        }
        return paths;
    }
}