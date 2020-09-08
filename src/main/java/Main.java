import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class Main {

  private static final HashMap<Integer, String> keys = new HashMap<Integer, String>();

  public static void main(String[] args) {
    try {
      for (; ; ) {
        final String con = args[0];
        final String user = args[1];
        final String pass = args[2];
        final String sshHost = args[3];
        final String sshLogin = args[4];
        final String sshPass = args[5];
        String query = "SELECT id, text FROM Plesk WHERE id = 0";
        String delete = "DELETE FROM Plesk WHERE id = ?";
        String update = "UPDATE Plesk SET id = id - 1 WHERE id >= ?";
        Connection conn = DriverManager.getConnection("jdbc:mysql://" + con + ":3306/admin_plesk2?useSSL=false&serverTimezone=UTC&characterEncoding=utf8", user, pass);
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
          String id = rs.getString("id");
          String text = rs.getString("text");
          keys.put(Integer.parseInt(id), text);
        }
        String command = "cd /; usr/sbin/plesk bin license -i " + keys.get(0).toString().trim();
        System.out.println(keys.get(0));
        runCommand(command, sshPass, sshLogin, sshHost);

        //remove from DB and HashMap
        keys.remove(0);
        PreparedStatement preparedStmt = conn.prepareStatement(delete);
        PreparedStatement preparedStmt2 = conn.prepareStatement(update);
        preparedStmt.setInt(1, 0);
        preparedStmt2.setInt(1, 0);
        preparedStmt.executeUpdate();
        preparedStmt2.executeUpdate();
        rs.close();
        statement.close();
        preparedStmt.close();
        preparedStmt2.close();
        conn.close();
        //sleep 11,5 days
        Thread.sleep(999999999);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  protected static void runCommand(String command, String pass, String login, String host) {
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
          if (i < 0)
            break;
          System.out.print(new String(tmp, 0, i));
        }
        if (channel.isClosed()) {
          System.out.println("exit-status: " + channel.getExitStatus());
          break;
        }
        try {
          Thread.sleep(1000);
        } catch (Exception ee) {

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