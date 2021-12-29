package db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ConnectionsData {

    private final String con;
    private final String user;
    private final String pass;
    private final String sshHost;
    private final String sshLogin;
    private final String sshPass;
    private final String databaseName;
}