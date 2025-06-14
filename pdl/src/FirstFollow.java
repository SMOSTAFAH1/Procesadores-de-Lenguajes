import java.util.*;

public class FirstFollow {

    private static final Map<String, List<Integer>> first = initializeFirst();
    private static final Map<String, List<Integer>> follow = initializeFollow();

    private static Map<String, List<Integer>> initializeFirst() {
        Map<String, List<Integer>> first = new HashMap<>();
        first.put("A", List.of(1, 5, 8, 10)); // boolean, int, string, void
        first.put("B", List.of(15, 3, 4, 6, 7, 9, 11)); // id, if, input, output, return, var, while
        first.put("C", List.of(15, 3, 4, 6, 7, 9, 11)); // id, if, input, output, return, var, while
        first.put("E", List.of(24, 19, 14, 13, 15)); // (, cadena, entero, id
        first.put("F", List.of(2)); // function
        first.put("G", List.of(25)); // !=, lambda
        first.put("H", List.of(1, 5, 8, 10)); // boolean, int, string, void
        first.put("K", List.of(17)); // ,, lambda
        first.put("L", List.of(19, 14, 13, 15)); // (, cadena, entero, id, lambda
        first.put("O", List.of(23)); // +, lambda
        first.put("P", List.of(26, 2, 15, 3, 4, 6, 7, 9, 11)); // eof, function, id, if, input, output, return, var, while
        first.put("Q", List.of(17)); // ,, lambda
        first.put("R", List.of(24, 19, 14, 13, 15)); // (, cadena, entero, id
        first.put("S", List.of(15, 4, 6, 7)); // id, input, output, return
        first.put("T", List.of(5, 1, 8));
        first.put("U", List.of(24,19,14,13,15));
        //first.put("U", List.of(19, 14, 13, 15)); // (, cadena, entero, id
        first.put("V", List.of(19, 14, 13, 15)); // (, cadena, entero, id
        //first.put("W", List.of(24)); // !, lambda
        first.put("X", List.of(19, 14, 13, 15)); // (, cadena, entero, id, lambda
        first.put("Y", List.of(19, 12, 16)); // (, --, =
        first.put("Z", List.of(12, 19)); // (, lambda
        return first;
    }

    private static Map<String, List<Integer>> initializeFollow() {
        Map<String, List<Integer>> follow = new HashMap<>();
        follow.put("A", List.of(20)); // )
        follow.put("B", List.of(26, 2, 22, 15, 3, 4, 6, 7, 9, 11)); // vacío
        follow.put("C", List.of(22)); // } 
        follow.put("E", List.of(20, 17, 18)); // ), ,, ;
        follow.put("F", List.of(26, 2, 15, 3, 4, 6, 7, 9, 11)); // vacío
        follow.put("G", List.of(20, 17, 18)); // ), ,, ;
        follow.put("H", List.of(15)); // vacío
        follow.put("K", List.of(20)); // )
        follow.put("L", List.of(20)); // )
        follow.put("O", List.of(25, 20, 17, 18)); // !=, ), ,, ;
        follow.put("P", List.of()); // vacío
        follow.put("Q", List.of(20)); // )
        follow.put("R", List.of(25, 20, 17, 18)); // !=, ), ,, ;
        follow.put("S", List.of(22)); // vacío
        follow.put("T", List.of(15));
        follow.put("U", List.of(25, 20, 23, 17, 18)); // !=, ), +, ,, ;
        follow.put("V", List.of(24, 25, 20, 23, 17, 18)); // !, !=, ), +, ,, ;
        //follow.put("W", List.of(25, 20, 23, 17, 18)); // !=, ), +, ,, ;
        follow.put("X", List.of(18)); // ;
        follow.put("Y", List.of(22)); // vacío
        follow.put("Z", List.of(24, 25, 20, 23, 17, 18)); // !, !=, ), +, ,, ;
        return follow;
    }

    public static Map<String, List<Integer>> getFirst() {
        return first;
    }

    public static Map<String, List<Integer>> getFollow() {
        return follow;
    }

    public static void printFirstFollow() {
        System.out.println("FIRST:");
        first.forEach((symbol, list) -> System.out.println("  " + symbol + " -> " + list));

        System.out.println("\nFOLLOW:");
        follow.forEach((symbol, list) -> System.out.println("  " + symbol + " -> " + list));
    }
}

