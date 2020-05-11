import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

interface Multiset<E> {

    /**
     * Add an element to the multiset.
     * It increases the multiplicity of the element by 1.
     */
    void add(E elem);

    /**
     * Remove an element from the multiset.
     * It decreases the multiplicity of the element by 1.
     */
    void remove(E elem);

    /**
     * Unite this multiset with another one. The result is the modified multiset (this).
     * It will contain all elements that are present in at least one of the initial multisets.
     * The multiplicity of each element is equal to the maximum multiplicity of
     * the corresponding elements in both multisets.
     */
    void union(Multiset<E> other);

    /**
     * Intersect this multiset with another one. The result is the modified multiset (this).
     * It will contain all elements that are present in the both multisets.
     * The multiplicity of each element is equal to the minimum multiplicity of
     * the corresponding elements in the intersecting multisets.
     */
    void intersect(Multiset<E> other);

    /**
     * Returns multiplicity of the given element.
     * If the set doesn't contain it, the multiplicity is 0
     */
    int getMultiplicity(E elem);

    /**
     * Check if the multiset contains an element,
     * i.e. the multiplicity > 0
     */
    boolean contains(E elem);

    /**
     * The number of unique elements,
     * that is how many different elements there are in a multiset.
     */
    int numberOfUniqueElements();

    /**
     * The size of the multiset, including repeated elements
     */
    int size();

    /**
     * The set of unique elements (without repeating)
     */
    Set<E> toSet();

//    void print();
}

class HashMultiset<E> implements Multiset<E> {

    private Map<E, Integer> map = new HashMap<>();

    @Override
    public void add(E elem) {
        // implement the method
        if (contains(elem)) {
            Integer integer = map.get(elem);
            map.replace(elem, ++integer);
        } else {
            map.put(elem, 1);
        }
    }

    @Override
    public void remove(E elem) {
        // implement the method
        if (contains(elem)) {
            Integer integer = map.get(elem);
            if (integer > 1) {
                map.replace(elem, --integer);
            } else {
                map.remove(elem);
            }
        }
    }

    @Override
    public void union(Multiset<E> other) {
        // implement the method
        Set<E> es = other.toSet();
        es.forEach(e -> {
            if (!contains(e)) {
                add(e);
            }
        });
    }

    @Override
    public void intersect(Multiset<E> other) {
        // implement the method
        Set<E> es = other.toSet();
        Map<E, Integer> newHashMap = Collections.synchronizedMap(new HashMap<>());
        for (E e : es) {
            if (contains(e)) {
                int multiplicity = other.getMultiplicity(e);
                int multiplicity1 = this.getMultiplicity(e);
                int max = Math.min(multiplicity, multiplicity1);
                newHashMap.put(e, max);
            }
        }
        this.map = newHashMap;
    }

    @Override
    public int getMultiplicity(E elem) {
        // implement the method
       return map.getOrDefault(elem, 0);
    }

    @Override
    public boolean contains(E elem) {
        // implement the method
        return map.containsKey(elem);
    }

    @Override
    public int numberOfUniqueElements() {
        // implement the method
        return map.size();
    }

    @Override
    public int size() {
        // implement the method
        return map.values().stream().mapToInt(i -> i).sum();
    }

    @Override
    public Set<E> toSet() {
        // Creating a new HashSet<> object helps us avoid ConcurrentModificationException.
        // It is thrown when we try to iterate over elements of Map and modify them at the same time
        return new HashSet<>(map.keySet());
    }

//    public void print() {
//        System.out.print("{");
//        map.forEach((k, v) -> System.out.print(k + ":" + v + " "));
//        System.out.print("}");
//    }
//
//    public static void main(String[] args) {
//        Multiset<String> multiset1 = new HashMultiset<>();
//        multiset1.add("a");
//        multiset1.add("a");
//        multiset1.add("b");
//        multiset1.add("b");
//        multiset1.add("b");
//        multiset1.add("c");
//
//        multiset1.print();
//        System.out.println();
//        System.out.println(multiset1.numberOfUniqueElements());
//        System.out.println(multiset1.size());
//        System.out.println("Mpc of a "  + multiset1.getMultiplicity("a"));
//        multiset1.remove("a");
//        multiset1.remove("a");
//        System.out.println("Mpc of a "  + multiset1.getMultiplicity("a"));
//        System.out.println(multiset1.numberOfUniqueElements());
//        System.out.println(multiset1.size());
//        multiset1.print();
//        System.out.println();
//        multiset1.print();
//        System.out.println();
//
//    }

}