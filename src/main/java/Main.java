import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;


public class Main {

  private static final String CONN = "jdbc:mysql://95.181.157.159:3306/admin_plesk2?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
  private static final String USER = "";
  private static final String PASS = "";
  private static final HashMap<Integer, String> keys = new HashMap<Integer, String>();


  public static void main(String[] args) {
    try {
      for (; ; ) {
        String query = "SELECT id, text FROM Plesk WHERE id = 0";
        String delete = "DELETE FROM Plesk WHERE id = ?";
        String update = "UPDATE Plesk SET id = id - 1 WHERE id >= ?";
        // String sql = "SELECT id, text FROM Plesk " + "ORDER BY id DESC";
        Connection conn = DriverManager.getConnection(CONN, USER, PASS);
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
          String id = rs.getString("id");
          String text = rs.getString("text");
          keys.put(Integer.parseInt(id), text);
          // System.out.println(id + ". " + text + "\n");
        }

        System.out.println(keys.get(0));

        System.out.println(RunCommandViaSsh.runCommand("pwd"));
        System.out.println(RunCommandViaSsh.runCommand("bin/rm -rf /etc/sw/keys/keys/key*"));
        System.out
            .println(RunCommandViaSsh.runCommand("usr/sbin/plesk bin license -i " + keys.get(0)));

        //remove from DB and HashMap
        keys.remove(0);
        PreparedStatement preparedStmt = conn.prepareStatement(delete);
        PreparedStatement preparedStmt2 = conn.prepareStatement(update);
        preparedStmt.setInt(1, 0);
        preparedStmt2.setInt(1, 0);
        preparedStmt.executeUpdate();
        preparedStmt2.executeUpdate();
        //sleep 11,5 days
        Thread.sleep(999999999);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}