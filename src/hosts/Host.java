package hosts;

public class Host {

    public String hostname;
    public HostStatus status;

    Host(String hostname) {
        this.hostname = hostname;
        this.status=HostStatus.FREE;
    }

}
