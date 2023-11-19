package scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node {
    String target;
    List<String> commands;
    List<Node> dependencies;
    TaskStatus status;

    Node(String target, List<String> commands) {
        this.target = target;
        this.commands = commands;
        this.dependencies = new ArrayList<>();
        this.status = TaskStatus.NOT_STARTED;
    }

//    void addDependency(Node node) {
//        dependencies.add(node);
//    }

    void execute() {
        try {
            System.out.println("Executing commands for target: " + target);
            for (String command : commands) {
                System.out.println("Executing command: " + command);
                // Simulate command execution
                Random random = new Random();
                int randomNumber = random.nextInt(2001) + 1000;
                Thread.sleep(randomNumber);
            }
            this.status = TaskStatus.FINISHED;
            System.out.println("Target completed: " + target);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
