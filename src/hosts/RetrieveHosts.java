package hosts;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class RetrieveHosts {

    public static List<Host> hosts = new ArrayList<>();

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

    public static void retreiveHostsFromList(String hostsList) {

        // Remove the brackets, quotes, and split the string
        String[] hostNames = hostsList.replaceAll("[\\[\\]'\"]", "").split(",");

        // Add trimmed host names to the list
        for (String hostName : hostNames) {
            hosts.add(new Host(hostName.trim()));
        }
        
    }




}