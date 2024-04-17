package uta.cse3310;
import java.util.Set;

public class ActiveUserList {
    private Set<String> activeUsers;

    public ActiveUserList(Set<String> activeUsers) {
        this.activeUsers = activeUsers;
    }
    public Set<String> getActiveUsers() {
        return activeUsers;
    }
    public void setActiveUsers(Set<String> activeUsers) {
        this.activeUsers = activeUsers;
    }
}
