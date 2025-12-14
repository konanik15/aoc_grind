package year2025.day8;

import year2025.Year2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day8 extends Year2025 {

    public static void main(String[] args) throws IOException {
        String contentTest = Files.readString(Path.of(PATH + "day8/test.txt"));
        String contentInput = Files.readString(Path.of(PATH + "day8/input.txt"));

        System.out.println(resolve1Star(contentTest, 10));
        System.out.println(resolve1Star(contentInput, 1000));

        System.out.println(resolve2Star(contentTest));
        System.out.println(resolve2Star(contentInput));
    }

    private static long resolve2Star(String contentTest) {
        var listOfPoints = getListOfPoints(contentTest);
        var distances = getUniqueDistances(listOfPoints);
        var circuitList = new ArrayList<LinkedHashSet<Point>>();

        var lastTwoPoints = new TwoElementStack<Point>();

        for (PointDistance pd : distances) {
            var circuitA = findCircuit(circuitList, pd.a);
            var circuitB = findCircuit(circuitList, pd.b);

            if (circuitA == null && circuitB == null) {
                circuitList.add(new LinkedHashSet<>(List.of(pd.a, pd.b)));
            } else if (circuitA == null) {
                circuitB.add(pd.a);
            } else if (circuitB == null) {
                circuitA.add(pd.b);
            } else if (circuitA != circuitB) {
                circuitA.addAll(circuitB);
                circuitList.remove(circuitB);
            }

            lastTwoPoints.push(pd.a);
            lastTwoPoints.push(pd.b);
            if (circuitList.size() == 1 && circuitList.getFirst().size() == listOfPoints.size()) break;
        }


        return (long) lastTwoPoints.first.x * lastTwoPoints.second.x;
    }

    private static long resolve1Star(String contentTest, int targetAttempts) {
        var listOfPoints = getListOfPoints(contentTest);
        var distances = getUniqueDistances(listOfPoints);

        var circuitList = new ArrayList<LinkedHashSet<Point>>();

        int processedPairs = 0;
        for (PointDistance pd : distances) {
            if (processedPairs >= targetAttempts) break;
            processedPairs++;

            var circuitA = findCircuit(circuitList, pd.a);
            var circuitB = findCircuit(circuitList, pd.b);

            if (circuitA == null && circuitB == null) {
                circuitList.add(new LinkedHashSet<>(List.of(pd.a, pd.b)));
            } else if (circuitA == null) {
                circuitB.add(pd.a);
            } else if (circuitB == null) {
                circuitA.add(pd.b);
            } else if (circuitA != circuitB) {
                circuitA.addAll(circuitB);
                circuitList.remove(circuitB);
            }
        }

        var sizes = circuitList.stream()
                .map(Set::size)
                .sorted((a, b) -> Integer.compare(b, a))
                .toList();

        return (long) sizes.get(0) * sizes.get(1) * sizes.get(2);
    }

    private static LinkedHashSet<Point> findCircuit(ArrayList<LinkedHashSet<Point>> circuitList, Point point) {
        return circuitList.stream()
                .filter(list -> list.contains(point))
                .findFirst()
                .orElse(null);
    }

    private static TreeSet<PointDistance> getUniqueDistances(ArrayList<Point> listOfPoints) {
        var distances = new TreeSet<PointDistance>();

        for (int i = 0; i < listOfPoints.size(); i++) {
            for (int j = 0; j < listOfPoints.size(); j++) {
                if (i > j) {

                    var p1 = listOfPoints.get(i);
                    var p2 = listOfPoints.get(j);

                    long dx = (long) p1.x - p2.x;
                    long dy = (long) p1.y - p2.y;
                    long dz = (long) p1.z - p2.z;

                    var distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                    distances.add(new PointDistance(p1, p2, distance));
                }

            }
        }

        return distances;
    }

    private static ArrayList<Point> getListOfPoints(String contentTest) {
        var lines = contentTest.replaceAll("\\r", "").split("\n");
        var listOfPoints = new ArrayList<Point>();
        for (String line : lines) {
            var arr = line.split(",");
            listOfPoints.add(new Point(arr[0], arr[1], arr[2]));
        }
        return listOfPoints;
    }

    record Point(Integer x, Integer y, Integer z) {

        public Point(String x, String y, String z) {
            this(Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
        }
    }

    record PointDistance(Point a, Point b, double distance) implements Comparable<PointDistance> {

        @Override
        public int compareTo(PointDistance other) {
            int distCmp = Double.compare(this.distance, other.distance);
            if (distCmp != 0) return distCmp;

            int aCmp = Integer.compare(this.a.hashCode(), other.a.hashCode());
            if (aCmp != 0) return aCmp;

            return Integer.compare(this.b.hashCode(), other.b.hashCode());
        }
    }


    static class TwoElementStack<T> {
        private T first;
        private T second;

        public void push(T element) {
            first = second;
            second = element;
        }
    }

}