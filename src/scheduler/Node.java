package scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import network.master.Master;

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


                //I should change code here

                Process process = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", "uniq $OAR_NODEFILE | awk 'NR==2'"});

                // Capture the output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                // Read the first line
                String node = reader.readLine();

                // Wait for the process to complete
                int exitCode = process.waitFor();
                process.destroy();

                if (exitCode == 0 && node != null) {
                    // Print or use the output
                    System.out.println("Second Node: " + node);
                    String[] arguments = {command, node};
                    Master.main(arguments);
                } else {
                    // Handle error
                    System.out.println("Error: Unable to retrieve the second node.");
                }


                //sleep a random time, to be updated later
                Random random = new Random();
                int randomNumber = random.nextInt(2001) + 1000;
                Thread.sleep(randomNumber);

            }
            this.status = TaskStatus.FINISHED;
            System.out.println("Target completed: " + target);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
