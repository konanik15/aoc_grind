package year2025.day2;

import year2025.Year2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day2 extends Year2025 {

    public static void main(String[] args) throws IOException {
        String contentTest = Files.readString(Path.of(PATH + "day2/test.txt"));
        String contentInput = Files.readString(Path.of(PATH + "day2/input.txt"));

        System.out.println(resolve1Star(contentTest));
        System.out.println(resolve1Star(contentInput));

        System.out.println(resolve2Star(contentTest));
        System.out.println(resolve2Star(contentInput));
    }

    private static String resolve2Star(String contentTest) {
        contentTest = contentTest.replaceAll("\\s+", "");
        long sum = 0;
        List<String> content = Arrays.stream(contentTest.split(",")).toList();
        for (String value : content) {
            long start = Long.parseLong(value.split("-")[0]);
            long end = Long.parseLong(value.split("-")[1]);

            for (long i = start; i <= end; i++) {
                if (isInvalid2(String.valueOf(i))) sum += i;
            }
        }
        return String.valueOf(sum);
    }

    private static boolean isInvalid2(String id) {
        var idLength = id.length();
        var isInvalid = false;
        for (int length = 1; length <= idLength / 2; length++) {
            if (idLength % length == 0) {
                isInvalid= true;
                var subString = id.substring(0, length);
                for (int i = length; i+length <= idLength; i+=length) {
                    var checkedSubString = id.substring(i, i + length);
                    if (!subString.equals(checkedSubString)) {
                        isInvalid= false;
                        break;
                    }
                }
            }
            if(isInvalid) {
                return true;
            }
        }
        return isInvalid;
    }

    private static String resolve1Star(String contentTest) {
        contentTest = contentTest.replaceAll("\\s+", "");
        long sum = 0;
        List<String> content = Arrays.stream(contentTest.split(",")).toList();
        for (String value : content) {


            long start = Long.parseLong(value.split("-")[0]);
            long end = Long.parseLong(value.split("-")[1]);

            for (long i = start; i <= end; i++) {
                if (isInvalid1(String.valueOf(i))) sum += i;
            }
        }
        return String.valueOf(sum);
    }


    private static boolean isInvalid1(String id) {
        if (id.length() % 2 == 1) return false;
        var half = id.length() / 2;
        return id.substring(0, half).equals(id.substring(half));
    }
}