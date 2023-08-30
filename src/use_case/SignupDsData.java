package use_case;

import java.time.LocalDateTime;

public class SignupDsData {

    private final String username;
    private String password;
    private final LocalDateTime creationTime;

    public SignupDsData(String username, String password, LocalDateTime creationTime) {
        this.username = username;
        this.password = password;
        this.creationTime = creationTime;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }


}
