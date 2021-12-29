import db.ConnectionsData;
import threads.ThreadGetDate;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length != 7) {
            throw new Exception("Wrong number of arguments");
        }

        final ConnectionsData connectionsData = new ConnectionsData(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
        Thread thread = new ThreadGetDate(connectionsData);
        thread.start();
    }
}