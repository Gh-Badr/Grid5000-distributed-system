package scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LocalScheduler scheduler = new LocalScheduler();

        Node n1 = new Node("T1", List.of("CommandA", "CommandB"));
        Node n2 = new Node("T2", List.of("CommandC"));
        Node n3 = new Node("T3", List.of("CommandD", "CommandE"));
        Node n4 = new Node("T4", List.of("CommandF"));
        Node n5 = new Node("T5", new ArrayList<>());
        Node n6 = new Node("T6", List.of("CommandG"));
        Node n7 = new Node("T7", List.of("CommandH", "CommandI","CommandJ"));

        Map<Node, List<Node>> dependencies = new HashMap<>();
        dependencies.put(n1, List.of(n2, n3,n4)); // n1 -> n2, n3 and n4
        dependencies.put(n3, List.of(n5,n6)); // n3 -> n5 et n6
        dependencies.put(n2, List.of(n7)); // n2 -> n7
        dependencies.put(n4, List.of(n7)); // n4 -> n7
        dependencies.put(n5, List.of(n7)); // n5 -> n7
        dependencies.put(n6, new ArrayList<>()); // n6 -> n7
        dependencies.put(n7, new ArrayList<>()); // n7 depends on nothing

        for (Map.Entry<Node, List<Node>> entry : dependencies.entrySet()) {
            scheduler.addNode(entry.getKey(), entry.getValue());
        }
        scheduler.executeTasks();
    }
}
