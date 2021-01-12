package lab04.version1;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Histogram {
    private final Map<String, Integer> elementsMap = new HashMap<>();
    private final Map<String, Integer> accessToBufferMap = new HashMap<>();

    public synchronized void addAccess(String key, int value) {
        this.accessToBufferMap.put(key, value);
    }

    public synchronized void addElements(String key, int value) {
        this.elementsMap.put(key, value);
    }

    private void drawMap(Map<String, Integer> map) {
        map.keySet().stream()
                .sorted(this::compareNames)
                .collect(Collectors.toList())
                .forEach(key -> System.out.println(key + " " + map.get(key)));
    }

    public synchronized void drawHistograms() {
        System.out.println("Number of processed elements: ");
        drawMap(this.elementsMap);
        System.out.println("\nNumber of accesses to the buffer: ");
        drawMap(this.accessToBufferMap);
    }

    private int compareNames(String name1, String name2) {
        String[] name1Array = name1.split("-");
        String[] name2Array = name2.split("-");

        int result = name1Array[0].compareTo(name2Array[0]);
        if (result != 0)
            return result;
        if (Integer.parseInt(name1Array[1]) < Integer.parseInt(name2Array[1]))
            return -1;
        return 1;
    }
}
