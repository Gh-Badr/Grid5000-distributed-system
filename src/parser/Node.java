package parser;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
// import java.util.concurrent.locks.Lock;
// import java.util.concurrent.locks.ReentrantLock;

import network.master.Master;
import hosts.*;


public class Node {

    // private static final Lock lock = new ReentrantLock();

    private String nodeName;
    private List<String> commands;
    public TaskStatus status;
    private boolean isFile = false;

    public Node(String nodeName, List<String> commands) {
        this.nodeName = nodeName;
        this.commands = commands;
        this.status = TaskStatus.NOT_STARTED;
    }

    public Node(){
        this.commands = new ArrayList<>();
        this.status = TaskStatus.NOT_STARTED;
    }

    public Node(String nodeName) {
        this.nodeName = nodeName;
        this.commands = new ArrayList<>();
        this.status = TaskStatus.NOT_STARTED;
    }
    public void execute() {
        
        try {
            System.out.println("Executing commands for nodeName: " + nodeName);
            for (String command : commands) {
                System.out.println("Executing command: " + command);
                // Simulate command execution

                String[] arguments=new String[0];
                Host thisHost=null;

                

                int n = (RetrieveHosts.hosts).size();
                int i = n;
                Random random = new Random();


                // Node.lock.lock();
                while (i == n) {
                    synchronized(RetrieveHosts.hosts){
                        i = 0;
                        for (Host host : RetrieveHosts.hosts) {
                            //System.out.println(host.hostname);
                            if (host.status == HostStatus.FREE) {
                                thisHost = host;
                                arguments = new String[]{command, thisHost.hostname};
                                thisHost.status = HostStatus.OCCUPIED;
                                break;
                            }
                            i++;
                        }
                    }
                    
                    // If no host was free, consider waiting or retrying after a delay
                    if (i == n) {
                        Thread.sleep(random.nextInt(201) + 100); // someDelay is a delay in milliseconds
                    }
                }

                // Node.lock.unlock();



                int responseCode = Master.master(arguments);

                if(responseCode==0){
                    thisHost.status = HostStatus.FREE;
                }else{
                    throw new RuntimeException("ABORT ! Task failed.");
                }
            }

            this.status = TaskStatus.FINISHED;
            System.out.println("Target completed: " + nodeName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNodeName() {
        return nodeName;
    }

    public boolean getIsFile(){
        return isFile;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public void setIsFile(boolean bool){
        this.isFile = bool ;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void addCommand(String command){
        commands.add(command);
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void clearCommands() {
        commands.clear();
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeName='" + nodeName + '\'' +
                ", commands=" + commands +
                '}';
    }
}
