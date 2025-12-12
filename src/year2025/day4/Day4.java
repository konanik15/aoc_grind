package year2025.day4;

import year2025.Year2025;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Arrays;

public class Day4 extends Year2025 {

    public static void main(String[] args) throws IOException {
        String contentTest = Files.readString(Path.of(PATH + "day4/test.txt"));
        String contentInput = Files.readString(Path.of(PATH + "day4/input.txt"));

        System.out.println(resolve1Star(contentTest));
        System.out.println(resolve1Star(contentInput));

        System.out.println(resolve2Star(contentTest));
        System.out.println(resolve2Star(contentInput));
    }

    private static int resolve1Star(String contentTest) {
        var content = Arrays.stream(contentTest.replaceAll("\\r", "").split("\n")).toList();

        var grid = content.stream().map(line -> line.chars()
                .mapToObj(c -> c == '@')
                .toArray(Boolean[]::new)
        ).toArray(Boolean[][]::new);

        return getAccessiblePoints(grid).size();
    }

    private static int resolve2Star(String contentTest) {
        var content = Arrays.stream(contentTest.replaceAll("\\r", "").split("\n")).toList();

        var grid = content.stream().map(line -> line.chars()
                .mapToObj(c -> c == '@')
                .toArray(Boolean[]::new)
        ).toArray(Boolean[][]::new);

        var accessiblePoints = getAccessiblePoints(grid);
        var sum = accessiblePoints.size();
        while (!accessiblePoints.isEmpty()) {
            removeAccessibleElements(grid, accessiblePoints);
            accessiblePoints = getAccessiblePoints(grid);
            sum += accessiblePoints.size();
        }


        return sum;
    }

    private static void removeAccessibleElements(Boolean[][] grid, ArrayDeque<Point> accessiblePoints) {
        while (!accessiblePoints.isEmpty()) {
            Point currentPoint = accessiblePoints.pop();
            grid[currentPoint.y][currentPoint.x] = false;
        }

    }

    private static ArrayDeque<Point> getAccessiblePoints(Boolean[][] grid) {
        var listOfAccessibleFields = new ArrayDeque<Point>();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x]) {
                    if (isAccessible(y, x, grid)) {
                        listOfAccessibleFields.add(new Point(x, y));
                    }
                }
            }
        }
        return listOfAccessibleFields;
    }

    private static boolean isAccessible(int y, int x, Boolean[][] grid) {
        var adjacentCounter = 0;
        final int maxRolls = 4;

        var adjacentToBorderIndex = grid[x].length - 1;

        var currentRow = y - 1;
        // Upper
        if (y > 0) {
            if (x > 0 && grid[currentRow][x - 1]) adjacentCounter++;
            if (grid[currentRow][x]) adjacentCounter++;
            if (x < adjacentToBorderIndex && grid[currentRow][x + 1]) adjacentCounter++;
        }

        // Middle
        currentRow = y;
        if (x > 0 && grid[currentRow][x - 1]) adjacentCounter++;
        if (x < adjacentToBorderIndex && grid[currentRow][x + 1]) adjacentCounter++;

        // Lower
        if (y < grid.length - 1) {
            currentRow = y + 1;
            if (x > 0 && grid[currentRow][x - 1]) adjacentCounter++;
            if (grid[currentRow][x]) adjacentCounter++;
            if (x < adjacentToBorderIndex && grid[currentRow][x + 1]) adjacentCounter++;
        }

        return adjacentCounter < maxRolls;
    }
}