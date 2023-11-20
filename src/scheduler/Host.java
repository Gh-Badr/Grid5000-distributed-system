package scheduler;

public class Host {

    String hostname;
    HostStatus status;

    Host(String hostname) {
        this.hostname = hostname;
        this.status=HostStatus.FREE;
    }

}
