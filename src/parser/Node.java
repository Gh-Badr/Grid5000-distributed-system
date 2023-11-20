import java.util.ArrayList;
import java.util.List;

public class Node {

    private String nodeName;
    private List<String> commands;

    public Node(){
        this.commands = new ArrayList<>();
    }

    public Node(String nodeName) {
        this.nodeName = nodeName;
        this.commands = new ArrayList<>();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
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

    @Override
    public String toString() {
        return "Node{" +
                "nodeName='" + nodeName + '\'' +
                ", commands=" + commands +
                '}';
    }
}
