package scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    static List<Host> hosts = new ArrayList<>();

    public static void retreiveHosts() {

        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", "uniq $OAR_NODEFILE | awk 'NR>1'"});

            // Capture the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            // Read each line and create Host objects
            while ((line = reader.readLine()) != null) {
                hosts.add(new Host(line.trim()));
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            process.destroy();

            if (exitCode == 0) {
                // Print the list of hosts
                for (Host host : hosts) {
                    System.out.println("Hostname: " + host.hostname + ", Status: " + host.status);
                }
            } else {
                // Handle error
                System.out.println("Error: Unable to retrieve the nodes.");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static void main(String[] args) throws InterruptedException {

        retreiveHosts();

        LocalScheduler scheduler = new LocalScheduler();

        Node n1 = new Node("T1", List.of("touch CommandA.txt", "touch CommandB.txt"));
        Node n2 = new Node("T2", List.of("touch CommandC.txt"));
        Node n3 = new Node("T3", List.of("touch CommandD.txt", "touch CommandE.txt"));
        Node n4 = new Node("T4", List.of("touch CommandF.txt"));
        Node n5 = new Node("T5", new ArrayList<>());
        Node n6 = new Node("T6", List.of("touch CommandG.txt"));
        Node n7 = new Node("T7", List.of("touch CommandH.txt", "touch CommandI.txt","touch CommandJ.txt"));

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
