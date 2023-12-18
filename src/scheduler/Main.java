package scheduler;

import parser.MakefileParser;
import parser.Node;
import hosts.RetrieveHosts;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        FileWriter fileWriter = null;
        long startTime0 = System.nanoTime();
        long endTime0 = System.nanoTime();
        long latency0 = endTime0 - startTime0;

        // RetrieveHosts.retreiveHosts();
        long startTime = System.nanoTime();
        RetrieveHosts.retreiveHostsFromList(args[0]);
        LocalScheduler scheduler = new LocalScheduler();

        MakefileParser parser = new MakefileParser();
        Map<Node,List<Node>> graph = parser.processFile("./scheduler/Makefile");

        for (Map.Entry<Node, List<Node>> entry : graph.entrySet()) {
            scheduler.addNode(entry.getKey(), entry.getValue());
        }
        scheduler.executeTasks();
        long endTime = System.nanoTime();
        long latency = endTime - startTime - latency0;
        try {
            fileWriter = new FileWriter("scheduler_results.csv", true);
            fileWriter.write((RetrieveHosts.hosts).size()+","+latency + "\n");  
            System.out.println("HELLO");
        } catch (Exception e) {
            System.out.println("Master exception In Message Transmission : " + e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Error closing FileWriter: " + e);
                }
            }
        }
        
    }

    
}
