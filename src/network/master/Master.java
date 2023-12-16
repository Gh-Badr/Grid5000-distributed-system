package network.master;

import network.node.PingPongInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;


public class Master {

    private static int executeACommand(String command,String host){
        int exitCode = -1;
        try {
            PingPongInterface exec = (PingPongInterface) Naming.lookup("rmi://"+host+":3000/PingPongObject");
            exitCode = exec.executeCommand(command);

            // Print the result
            if (exitCode == 0) {
                System.out.println("The File has been created.");
            } else {
                System.out.println("Error executing command. Exit code: " + exitCode);
            }



        } catch (Exception e) {
            System.out.println("Master exception in File Creation : " + e);
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

    public static int master(String[] args) {

        if (args.length > 1) {
            getMessage(args[0],args[1]);
            return executeACommand(args[0],args[1]);
        } else {
            System.out.println("Please provide command and a host as a command-line argument.");
            return -1; //pour tester
        }
    }
}
