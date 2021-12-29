package threads;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import db.ConnectionsData;
import db.DataBase;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class ThreadGetDate extends Thread {

    private final ConnectionsData connectionsData;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

    @Override
    public void run() {
        while (true) {
            try {
                String[] commandFromSHH = runCommand("plesk bin keyinfo -l", connectionsData.getSshPass(), connectionsData.getSshLogin(), connectionsData.getSshHost());

                if (commandFromSHH != null && commandFromSHH.length > 5) {
                    StringBuffer stringBuffer = new StringBuffer(commandFromSHH[3].replaceAll("lim_date: ", "").trim());
                    stringBuffer.insert(4, ".").insert(7, ".").append(" 00:00");

                    LocalDateTime nowDate = LocalDateTime.now();
                    LocalDateTime dateTime = LocalDateTime.parse(stringBuffer, formatter);
                    String formattedDateTime = dateTime.format(formatter);

                    System.out.println("nowDate " + nowDate.format(formatter));
                    System.out.println("dateTime " + formattedDateTime);

                    if (nowDate.plusDays(1L).isAfter(dateTime)) {
                        System.out.println("We here");
                        DataBase dataBase = new DataBase(connectionsData);
                        dataBase.getRowsInTable();
                    }
                }
                Thread.sleep(3600000L);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
    }

    private String[] runCommand(String command, String pass, String login, String host) {
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
//                    System.out.print(new String(tmp, 0, i));
                    return new String(tmp, 0, i).split("\n");
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
        return null;
    }
}
