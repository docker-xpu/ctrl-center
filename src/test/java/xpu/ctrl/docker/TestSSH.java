package xpu.ctrl.docker;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class TestSSH {
    /**
     * Run SSH command.
     * @param host
     * @param username
     * @param password
     * @param cmd
     */
    public static int runSSH(String host, String username, String password, String cmd) throws IOException {
        System.out.println("running SSH cmd [" + cmd + "]");

        Connection conn = getOpenedConnection(host, username, password);
        Session sess = conn.openSession();
        sess.execCommand(cmd);

        InputStream stdout = new StreamGobbler(sess.getStdout());
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

        while (true) {
            String line = br.readLine();
            if (line == null){
                break;
            }
            System.out.println(line);
        }
        sess.close();
        conn.close();
        return sess.getExitStatus();
    }
    /**
     * return a opened Connection
     */
    private static Connection getOpenedConnection(String host, String username, String password) throws IOException {
        System.out.println("connecting to " + host + " with user " + username + " and pwd " + password);
        //添加SSH连接端口，默认22
        Connection conn = new Connection(host, 22);
        conn.connect();
        boolean isAuthenticated = conn.authenticateWithPassword(username, password);
        if (!isAuthenticated) throw new IOException("Authentication failed.");
        return conn;
    }

    public static void main(String[] args) throws Exception{
        //String cmd = "echo \"Hello SSH\" >> /root/java-ssh.txt";
        String cmd = "echo \"ls\n" +
                "mkdir bbb\n" +
                "cd bbb\n" +
                "wget http://img.zouchanglin.cn/cmake-3.14.0.tar.gz\n" +
                "tar -zxvf cmake-3.14.0.tar.gz &\"" +
                "./cmake-3.14.0/.configure & make & make install>> run.sh & chmod a+x run.sh & ./run.sh";
        runSSH("192.168.2.2", "root", "123456", cmd);
        //runSSH("139.159.234.67", "root", "lhl123456an+", cmd);
    }
}