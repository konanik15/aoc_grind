package year2025.day9;

import year2025.Year2025;

import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

public class Day9 extends Year2025 {

    public static void main(String[] args) throws IOException {
        String contentTest = Files.readString(Path.of(PATH + "day9/test.txt"));
        String contentInput = Files.readString(Path.of(PATH + "day9/input.txt"));

        System.out.println(resolve1Star(contentTest));
        System.out.println(resolve1Star(contentInput));

        System.out.println(resolve2Star(contentTest));
        System.out.println(resolve2Star(contentInput));
    }

    private static BigInteger resolve2Star(String contentTest) {
        var points = getListOfPoints(contentTest);
        var pointsSet = new HashSet<>(points);

        var largestArea = BigInteger.ZERO;

        for (Point point : points) {
            for (Point sp : points) {
                if (rectangleIsValid(point, sp, points, pointsSet)) {
                    BigInteger a = BigInteger.valueOf(Math.abs(point.x - sp.x) + 1);
                    BigInteger b = BigInteger.valueOf(Math.abs(point.y - sp.y) + 1);
                    var currentArea = a.multiply(b);
                    if (largestArea.compareTo(currentArea) < 0) {
                        largestArea = currentArea;
                    }
                }
            }
        }

        return largestArea;
    }

    private static boolean rectangleIsValid(Point a, Point b,
                                            ArrayList<Point> polygon,
                                            HashSet<Point> pointsSet) {
        int minX = Math.min(a.x, b.x);
        int maxX = Math.max(a.x, b.x);
        int minY = Math.min(a.y, b.y);
        int maxY = Math.max(a.y, b.y);

        Point topLeft = new Point(minX, minY);
        Point topRight = new Point(maxX, minY);
        Point bottomLeft = new Point(minX, maxY);
        Point bottomRight = new Point(maxX, maxY);

        if (!isInsideOrOnBoundary(topLeft, polygon, pointsSet)) return false;
        if (!isInsideOrOnBoundary(topRight, polygon, pointsSet)) return false;
        if (!isInsideOrOnBoundary(bottomLeft, polygon, pointsSet)) return false;
        if (!isInsideOrOnBoundary(bottomRight, polygon, pointsSet)) return false;

        Line[] rectEdges = {
                new Line(topLeft, topRight),
                new Line(topRight, bottomRight),
                new Line(bottomRight, bottomLeft),
                new Line(bottomLeft, topLeft)
        };

        int n = polygon.size();
        for (int i = 0; i < n; i++) {
            Point p1 = polygon.get(i);
            Point p2 = polygon.get((i + 1) % n);
            Line polyEdge = new Line(p1, p2);

            for (Line rectEdge : rectEdges) {
                if (linesIntersect(rectEdge, polyEdge)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isInsideOrOnBoundary(Point p, ArrayList<Point> polygon,
                                                HashSet<Point> pointsSet) {
        if (pointsSet.contains(p)) {
            return true;
        }

        int crossings = 0;
        int n = polygon.size();
        for (int i = 0; i < n; i++) {
            Point p1 = polygon.get(i);
            Point p2 = polygon.get((i + 1) % n);

            if ((p1.y > p.y) != (p2.y > p.y)) {
                double xIntersection = p1.x + (double) (p.y - p1.y) / (p2.y - p1.y) * (p2.x - p1.x);
                if (p.x < xIntersection) {
                    crossings++;
                }
            }
        }

        return crossings % 2 == 1;
    }

    private static boolean linesIntersect(Line line1, Line line2) {
        Point p1 = line1.start;
        Point p2 = line1.end;
        Point p3 = line2.start;
        Point p4 = line2.end;

        long d1 = crossProduct(p1, p2, p3);
        long d2 = crossProduct(p1, p2, p4);
        long d3 = crossProduct(p3, p4, p1);
        long d4 = crossProduct(p3, p4, p2);

        return ((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
                ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0));
    }

    private static long crossProduct(Point a, Point b, Point c) {
        return (long) (b.x - a.x) * (c.y - a.y) - (long) (b.y - a.y) * (c.x - a.x);
    }

    record Line(Point start, Point end) {
    }

    private static BigInteger resolve1Star(String contentTest) {
        var points = getListOfPoints(contentTest);

        var largestArea = BigInteger.ZERO;

        for (Point point : points) {
            for (Point sp : points) {
                BigInteger a = BigInteger.valueOf(Math.abs(point.x - sp.x) + 1);
                BigInteger b = BigInteger.valueOf(Math.abs(point.y - sp.y) + 1);
                var currentArea = a.multiply(b);
                if (largestArea.compareTo(currentArea) < 0) largestArea = currentArea;
            }
        }


        return largestArea;
    }

    private static ArrayList<Point> getListOfPoints(String contentTest) {
        var lines = contentTest.replaceAll("\\r", "").split("\n");
        var listOfPoints = new ArrayList<Point>();
        for (String line : lines) {
            var arr = line.split(",");
            listOfPoints.add(new Point(Integer.parseInt(arr[0]), Integer.parseInt(arr[1])));
        }
        return listOfPoints;
    }

}