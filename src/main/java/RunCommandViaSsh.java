import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.InputStream;

public class RunCommandViaSsh {

  private static final String SSH_HOST = "95.181.157.159";
  private static final String SSH_LOGIN = "";
  private static final String SSH_PASSWORD = "";

  protected static void runCommand(String command) {
    java.util.Properties config = new java.util.Properties();
    config.put("StrictHostKeyChecking", "no");
    JSch jsch = new JSch();
    try {
      Session session = jsch.getSession(SSH_LOGIN, SSH_HOST, 22);
      session.setPassword(SSH_PASSWORD);
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
