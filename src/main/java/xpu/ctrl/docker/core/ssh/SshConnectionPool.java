package xpu.ctrl.docker.core.ssh;


import ch.ethz.ssh2.Connection;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.session.ClientSession;
import xpu.ctrl.docker.entity.HostLicense;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class SshConnectionPool {

    private static final Map<String, Connection> pool = Maps.newHashMap();
    private static SshConnectionPool connectionPool = new SshConnectionPool();

    private SshConnectionPool() {
    }

    public static SshConnectionPool getSSHConnectionPoolInstance(){
        return connectionPool;
    }


    public Connection getConnectionByIP(String ip){
        return pool.get(ip);
    }

    public Connection getConnectionByIP(String ip, HostLicense hostLicense){
        Connection connection = pool.get(ip);
        if(connection != null) return connection;
        try {
            connection = getOpenedConnection(ip, hostLicense.getLicenseName(), hostLicense.getLicensePasswd());
            if(connection != null){
                pool.put(ip, connection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnectionByIP(String ip, String username,String passwd){
        Connection connection = pool.get(ip);
        if(connection != null) return connection;
        try {
            connection = getOpenedConnection(ip, username, passwd);
            if(connection != null){
                pool.put(ip, connection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Connection getOpenedConnection(String host, String username, String password) throws IOException {
        Connection conn = new Connection(host, 22);
        conn.connect();
        boolean isAuthenticated = conn.authenticateWithPassword(username, password);
        if (!isAuthenticated) throw new IOException("Authentication failed.");
        log.info("【成功获取SSH连接】{}", host);
        return conn;
    }

    public void clentTest() throws IOException {
        String cmd="ifconfig";
        SshClient client= SshClient.setUpDefaultClient();
        client.start();
        ClientSession session = client.connect("bellring", "10.2.48.179", 22).getSession();
        session.addPasswordIdentity("123456");
        if(!session.auth().isSuccess())
            System.out.println("auth failed");
        ChannelExec channelExec = session.createExecChannel(cmd);

        client.stop();
    }
}