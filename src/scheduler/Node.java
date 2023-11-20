package scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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

                String[] arguments=new String[0];
                Host thisHost=null;


                for (Host host : Main.hosts) {
                    System.out.println(host.hostname);
                    if (host.status == HostStatus.FREE) {
                        thisHost = host;
                        arguments = new String[]{command, thisHost.hostname};
                        thisHost.status = HostStatus.OCCUPIED;
                        break;
                    }
                }

                int responseCode = Master.master(arguments);

                //sleep a random time, to be updated later
                Random random = new Random();
                int randomNumber = random.nextInt(2001) + 1000;
                Thread.sleep(randomNumber);

                if(responseCode==0){
                    thisHost.status = HostStatus.FREE;
                }else{
                    throw new RuntimeException("ABORT ! Task failed.");
                }



            }

            this.status = TaskStatus.FINISHED;
            System.out.println("Target completed: " + target);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
