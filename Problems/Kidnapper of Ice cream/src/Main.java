import java.util.*;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        List<String> collect = Arrays.stream(scanner.nextLine().split("\\s+")).collect(Collectors.toList());
        List<String> collect1 = Arrays.stream(scanner.nextLine().split("\\s+")).collect(Collectors.toList());
        if (collect.containsAll(collect1) && collect.size() > collect1.size()) System.out.println("You get money");
        else {
            System.out.println("You are busted");
        }
    }
}