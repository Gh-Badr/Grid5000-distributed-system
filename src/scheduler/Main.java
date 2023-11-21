package scheduler;

import parser.MakefileParser;
import parser.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LocalScheduler scheduler = new LocalScheduler();

        MakefileParser parser = new MakefileParser();
        Map<Node,List<Node>> graph = parser.processFile("C:\\Users\\Kamal\\Desktop\\ContainerTwo\\SD v2.0\\Grid5000-distributed-system\\src\\scheduler\\MakeFile.txt");

        for (Map.Entry<Node, List<Node>> entry : graph.entrySet()) {
            scheduler.addNode(entry.getKey(), entry.getValue());
        }
        scheduler.executeTasks();
    }
}
