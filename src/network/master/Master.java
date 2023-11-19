package network.master;

import network.node.PingPongInterface;

import java.rmi.Naming;


public class Master {

    private static void executeACommand(String command,String host){
        try {
            PingPongInterface exec = (PingPongInterface) Naming.lookup("rmi://"+host+":3000/PingPongObject");
            int exitCode = exec.executeCommand(command);

            // Print the result
            if (exitCode == 0) {
                System.out.println("The File has been created.");
            } else {
                System.out.println("Error executing command. Exit code: " + exitCode);
            }

        } catch (Exception e) {
            System.out.println("Master exception in File Creation : " + e);
        }
    }

    private static void getMessage(String command, String host) {
        try {
            PingPongInterface stub = (PingPongInterface) Naming.lookup("rmi://"+host+":3000/PingPongObject");
            String response = stub.ping(command);
            System.out.println("Response => " + response);
        } catch (Exception e) {
            System.out.println("Master exception In Message Transmission : " + e);
        }
    }

    public static void main(String[] args) {

        if (args.length > 1) {
            getMessage(args[0],args[1]);
            executeACommand(args[0],args[1]);
        } else {
            System.out.println("Please provide command and a host as a command-line argument.");
        }
    }
}
