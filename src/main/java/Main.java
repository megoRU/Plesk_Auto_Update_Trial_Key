import com.jcraft.jsch.JSchException;

public class Main {

  public static void main(String[] args) throws JSchException {
    //bin/rm -rf /etc/sw/keys/keys/key*
    //usr/sbin/plesk bin license -i ${key}



    System.out.println(RunCommandViaSsh.runCommand("pwd"));
    System.out.println(RunCommandViaSsh.runCommand("bin/rm -rf /etc/sw/keys/keys/key*"));
    System.out.println(RunCommandViaSsh.runCommand("usr/sbin/plesk bin license -i ${key}"));
  }
}