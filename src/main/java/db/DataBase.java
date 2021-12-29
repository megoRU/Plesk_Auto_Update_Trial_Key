package db;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.AllArgsConstructor;
import model.Plesk;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DataBase {

    private final ConnectionsData connectionsData;
    private static final List<Plesk> keys = new ArrayList<>();

    public void getRowsInTable() {
        try {
            final String query = "SELECT id, text FROM Plesk";
            final String delete = "DELETE FROM Plesk WHERE id = ";

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://" + connectionsData.getCon() + ":3306/" + connectionsData.getDatabaseName()
                            + "?useSSL=false&serverTimezone=UTC&characterEncoding=utf8", connectionsData.getUser(), connectionsData.getPass());

            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String id = rs.getString("id");
                String text = rs.getString("text");
                keys.add(new Plesk(id, text));
            }

            String command = "cd /; usr/sbin/plesk bin license -i " + keys.get(0).getText().trim();

            System.out.println(keys.get(0).getText());

            runCommand(command, connectionsData.getSshHost(), connectionsData.getSshLogin(), connectionsData.getSshPass());

            PreparedStatement preparedStmt = conn.prepareStatement(delete + keys.get(0).getId());
            preparedStmt.executeQuery();

            //Close con
            preparedStmt.close();
            rs.close();
            statement.close();
            keys.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runCommand(String command, String pass, String login, String host) {
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession(login, host, 22);
            session.setPassword(pass);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                    Thread.currentThread().interrupt();
                }
            }
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }
}
