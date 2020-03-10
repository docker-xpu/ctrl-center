package xpu.ctrl.docker.core.ssh;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SshConnectionPoolTest {
    @Test
    public void test(){
        SshConnectionPool instance = SshConnectionPool.getSSHConnectionPoolInstance();
        Connection connection = instance.getConnectionByIP("192.168.2.2", "root", "123456");
        try {
            Session session = connection.openSession();
            session.execCommand("ls -ahl");
            InputStream stdout = new StreamGobbler(session.getStdout());
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

            while (true) {
                String line = br.readLine();
                if (line == null){
                    break;
                }
                System.out.println(line);
            }
            session.close();
            Connection connectionByIP = instance.getConnectionByIP("192.168.2.2");
            Session openSession = connectionByIP.openSession();
            openSession.execCommand("ls");

            InputStream stdout2 = new StreamGobbler(openSession.getStdout());
            BufferedReader b2 = new BufferedReader(new InputStreamReader(stdout2));

            while (true) {
                String line = b2.readLine();
                if (line == null){
                    break;
                }
                System.out.println(line);
            }
            openSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}