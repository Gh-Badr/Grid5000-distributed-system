package scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LocalScheduler {
    Map<Node, List<Node>> graph = new HashMap<>();

    void addNode(Node node, List<Node> dependencies) {
        graph.put(node, dependencies);
    }

    void executeTasks() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        while (!allTasksCompleted()) {
            for (Map.Entry<Node, List<Node>> entry : graph.entrySet()) {
                Node node = entry.getKey();
                if (canBeExecuted(node)) {
                    node.setStatus(TaskStatus.IN_PROGRESS);
                    executor.submit(node::execute);
                }
            }
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    boolean allTasksCompleted() {
        return graph.keySet().stream().allMatch(n -> n.getStatus() == TaskStatus.FINISHED);
    }

    boolean canBeExecuted(Node node) {
        if (node.getStatus() != TaskStatus.NOT_STARTED) {
            return false;
        }
        return graph.get(node).stream().allMatch(dep -> dep.getStatus() == TaskStatus.FINISHED);
    }
}