package scheduler;

import parser.MakefileParser;
import parser.Node;
import hosts.RetrieveHosts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        RetrieveHosts.retreiveHosts();
        LocalScheduler scheduler = new LocalScheduler();

        MakefileParser parser = new MakefileParser();
        Map<Node,List<Node>> graph = parser.processFile("src/scheduler/Makefile.txt");

        for (Map.Entry<Node, List<Node>> entry : graph.entrySet()) {
            scheduler.addNode(entry.getKey(), entry.getValue());
        }
        scheduler.executeTasks();
    }
}
