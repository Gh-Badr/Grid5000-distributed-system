package network.master;

import network.node.PingPongInterface;
import parser.*;
import scheduler.LocalScheduler;

import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.util.List;


public class Master {

    private static int executeACommand(String command,String host,String master,Node node){
        int exitCode = -1;
        try {
            List<Node> deps = LocalScheduler.graph.get(node);
            if(deps !=null){
                for (Node dep : deps) {
                    if(hasFileExtension(dep.getNodeName())) sendFile(master,host,dep.getNodeName());
                    else System.out.println(dep.getNodeName());
                }
            }
            PingPongInterface exec = (PingPongInterface) Naming.lookup("rmi://"+host+":3000/PingPongObject");
            exitCode = exec.executeCommand(command);

            // Print the result
            if (exitCode == 0) {
                System.out.println("The File has been created.");
                // scp target.txt from host to master
                if(hasFileExtension(node.getNodeName())) sendFile(host,master,node.getNodeName());
            } else {
                System.out.println("Error executing command. Exit code: " + exitCode);
            }



        } catch (Exception e) {
            System.out.println("Master exception in File Creation : ");
            e.printStackTrace();
        }
        return exitCode;
    }

    private static void getMessage(String command, String host) {
        FileWriter fileWriter = null;
        long startTime0 = System.nanoTime();
        long endTime0 = System.nanoTime();
        long latency0 = endTime0 - startTime0;
        try {
            PingPongInterface stub = (PingPongInterface) Naming.lookup("rmi://"+host+":3000/PingPongObject");
            long startTime = System.nanoTime();
            String response = stub.ping(command,host);
            long endTime = System.nanoTime();
            long latency = endTime - startTime - latency0;
            // Écriture dans le fichier CSV
            fileWriter = new FileWriter("latency_optimized_results.csv", true); // 'true' pour append au fichier
            fileWriter.write(host + "," + latency + "\n"); // Format CSV : host,latency
            System.out.println("Response => " + response);
            System.out.println("Latency: " + latency + " nanoseconds");
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
    private static void sendFile(String source,String destination, String target){
        if(Node.hasFileExtension(target)){
//            System.out.println("Sending " + source + " to " + destination + " using file "+target);
//            String source = "petitprince-9.luxembourg.grid5000.fr:try.txt";
//            String destination = "petitprince-11.luxembourg.grid5000.fr";
            String command = "scp " + source + ":" + target + " " + destination + ":~";
            System.out.println("+++++++++ "+command);

            try {
                Process process = Runtime.getRuntime().exec(command);
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("File copied successfully");
                } else {
                    System.out.println("SCP failed with exit code " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean hasFileExtension(String fileName) {
        return fileName.contains(".") && fileName.lastIndexOf('.') > 0;
    }

    public static int master(String[] args,String masterName, Node node) {

        if (args.length > 1) {
            getMessage(args[0],args[1]);
            return executeACommand(args[0],args[1],masterName,node);
        } else {
            System.out.println("Please provide command and a host as a command-line argument.");
            return -1; //pour tester
        }
    }
}
