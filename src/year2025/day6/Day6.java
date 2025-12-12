package year2025.day6;

import year2025.Year2025;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day6 extends Year2025 {

    public static void main(String[] args) throws IOException {
        String contentTest = Files.readString(Path.of(PATH + "day6/test.txt"));
        String contentInput = Files.readString(Path.of(PATH + "day6/input.txt"));

        System.out.println(resolve1Star(contentTest, 3));
        System.out.println(resolve1Star(contentInput, 4));

        System.out.println(resolve2Star(contentTest, 3));
        System.out.println(resolve2Star(contentInput, 4));
    }

    private static BigInteger resolve1Star(String contentTest, int numberOfLines) {
        contentTest = contentTest.replaceAll("\\r", "");
        String[] parts = contentTest.split("\n");
        var numbers = Stream.of(Arrays.copyOfRange(parts, 0, numberOfLines))
                .map(s -> Arrays.stream(s.trim().split("\\s+"))
                        .map(Long::parseLong)
                        .map(BigInteger::valueOf).toList()).toList();

        var operations = Arrays.stream(parts[numberOfLines].split("\\s+")).toList();

        return getSum(numbers, operations);
    }

    private static BigInteger resolve2Star(String contentTest, int numberOfLines) {
        contentTest = contentTest.replaceAll("\\r", "");
        String[] parts = contentTest.split("\n");
        var numbers = Stream.of(Arrays.copyOfRange(parts, 0, numberOfLines)).toList();
        var sum = BigInteger.ZERO;


        var operations = parts[numberOfLines].toCharArray();
        var currentOperation = operations[0];
        List<BigInteger> currentNumbers = new ArrayList<>();
        for (int x = 0; x < operations.length; x++) {
            StringBuilder sb = new StringBuilder();
            if (x + 1 < operations.length && operations[x + 1] != ' ') {
                sum = getSum(currentNumbers, currentOperation, sum);
                currentNumbers = new ArrayList<>();
                currentOperation = operations[x + 1];
                continue;
            }
            for (String number : numbers) {
                if (number.charAt(x) != ' ')
                    sb.append(number.charAt(x));
            }

            currentNumbers.add(BigInteger.valueOf(Long.parseLong(sb.toString())));

        }
        sum = getSum(currentNumbers, currentOperation, sum);


        return sum;
    }

    private static BigInteger getSum(List<BigInteger> currentNumbers, char currentOperation, BigInteger sum) {

        var currentOperationResult = currentNumbers.getFirst();
        for (int y = 1; y < currentNumbers.size(); y++) {
            if (currentOperation == '+') {
                currentOperationResult = currentOperationResult.add(currentNumbers.get(y));
            } else {
                currentOperationResult = currentOperationResult.multiply(currentNumbers.get(y));
            }
        }
        return sum.add(currentOperationResult);
    }

    private static BigInteger getSum(List<List<BigInteger>> numbers, List<String> operations) {
        var sum = BigInteger.ZERO;
        for (int x = 0; x < numbers.getFirst().size(); x++) {
            var currentOperation = numbers.getFirst().get(x);
            for (int y = 1; y < numbers.size(); y++) {
                if (operations.get(x).equals("+")) {
                    currentOperation = currentOperation.add(numbers.get(y).get(x));
                } else {
                    currentOperation = currentOperation.multiply(numbers.get(y).get(x));
                }
            }
            sum = sum.add(currentOperation);
        }
        return sum;
    }

}