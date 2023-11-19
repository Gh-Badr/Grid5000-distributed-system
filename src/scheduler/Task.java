package scheduler;

import java.util.HashSet;
import java.util.Set;
public class Task {
    String id;
    Set<String> dependencies;
    TaskStatus status;

    Task(String id) {
        this.id = id;
        this.dependencies = new HashSet<>();
        this.status = TaskStatus.NOT_STARTED;
    }

    void addDependency(String idTache) {
        dependencies.add(idTache);
    }

    void execute() {
        try {
            System.out.println("Execution of the task : " + id);
            Thread.sleep(1000);
            this.status = TaskStatus.FINISHED;
            System.out.println("Task ended: " + id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}