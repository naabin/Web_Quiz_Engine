import java.util.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int[] ints = Arrays.stream(scanner.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        int totalPairs = Integer.parseInt(scanner.nextLine());
        SortedMap<Integer, String> integerMap = new TreeMap<>();
        while (totalPairs > 0) {
            String[] s = scanner.nextLine().split(" ");
            integerMap.put(Integer.parseInt(s[0]), s[1]);
            totalPairs--;
        }
        SortedMap<Integer, String> integerStringSortedMap = integerMap.subMap(ints[0], ints[1] + 1);
        integerStringSortedMap.forEach((key, value) -> System.out.println(key + " " + value));
    }
}