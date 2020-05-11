
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;


class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String s1 = scanner.nextLine().toLowerCase().strip();
        String s2 = scanner.nextLine().toLowerCase().strip();

        if (Objects.equals(getCharMap(s1), getCharMap(s2))) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }

    }

    static SortedMap<String, Integer> getCharMap(String s) {
        String[] split = s.split("");
        SortedMap<String, Integer> charMap = new TreeMap<>();
        for (String string : split) {
            charMap.put(string, charMap.getOrDefault(string, 0) + 1);
        }
        return charMap;
    }
}