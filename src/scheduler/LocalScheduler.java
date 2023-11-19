package scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LocalScheduler {
    Map<String, Task> tasks = new HashMap<>();

    void addTask(Task task) {
        tasks.put(task.id, task);
    }

    void executeTasks() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        while (!allTasksCompleted()) {
            for (Task task : tasks.values()) {
                if (canBeExecuted(task)) {
                    task.status = TaskStatus.IN_PROGRESS;
                    executor.submit(task::execute);
                }
            }
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    boolean allTasksCompleted() {
        return tasks.values().stream().allMatch(t -> t.status == TaskStatus.FINISHED);
    }

    boolean canBeExecuted(Task task) {
        if (task.status != TaskStatus.NOT_STARTED) {
            return false;
        }
        return task.dependencies.stream().allMatch(idDep -> tasks.get(idDep).status == TaskStatus.FINISHED);
    }
}