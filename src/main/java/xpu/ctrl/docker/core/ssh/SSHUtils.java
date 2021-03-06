package xpu.ctrl.docker.core.ssh;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class SSHUtils {
    private static final String ENCODING = "UTF-8";

    public static Session getJSchSession(DestHost destHost) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(destHost.getUsername(), destHost.getHost(), destHost.getPort());
        session.setPassword(destHost.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");//第一次访问服务器不用输入yes
        session.setTimeout(destHost.getTimeout());
        session.connect();
        return session;
    }

    public static String execCommandByJSch(Session session, String command, String resultEncoding) throws IOException, JSchException {
        //1.默认方式，执行单句命令
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        InputStream in = channelExec.getInputStream();
        channelExec.setCommand(command);
        channelExec.setErrStream(System.err);
        channelExec.connect();
        String result = IOUtils.toString(in, resultEncoding);
        channelExec.disconnect();
        return result;
    }

    public static String execCommandByShell(Session session, String ip, String version)throws IOException,JSchException{
        String result = "";
        //2.尝试解决 远程ssh只能执行一句命令的情况
        ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
        InputStream inputStream = channelShell.getInputStream();//从远端到达的数据  都能从这个流读取到
        channelShell.setPty(true);
        channelShell.connect();

        OutputStream outputStream = channelShell.getOutputStream();//写入该流的数据  都将发送到远程端
        //使用PrintWriter 就是为了使用println 这个方法
        //好处就是不需要每次手动给字符加\n
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println("cd /root");
        //printWriter.println("sh deploy.sh 192.168.2.2 v1.38 6060");
        printWriter.println(String.format("sh deploy.sh %s %s 6060", ip, version));
        printWriter.println("exit");//为了结束本次交互
        printWriter.flush();//把缓冲区的数据强行输出

        byte[] tmp = new byte[1024];
        while(true){
            while(inputStream.available() > 0){
                int i = inputStream.read(tmp, 0, 1024);
                if(i < 0) break;
                String s = new String(tmp, 0, i);
                if(s.indexOf("--More--") >= 0){
                    outputStream.write((" ").getBytes());
                    outputStream.flush();
                }
                System.out.println(s);
            }
            if(channelShell.isClosed()){
                System.out.println("exit-status:"+channelShell.getExitStatus());
                break;
            }
            try{Thread.sleep(1000);}catch(Exception e){}

        }
        outputStream.close();
        inputStream.close();
        channelShell.disconnect();
        session.disconnect();
        System.out.println("DONE");
        return result;
    }

    public static String execCommandByShellDeploy(Session session)throws IOException,JSchException{
        String result = "";
        //2.尝试解决 远程ssh只能执行一句命令的情况
        ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
        InputStream inputStream = channelShell.getInputStream();//从远端到达的数据  都能从这个流读取到
        channelShell.setPty(true);
        channelShell.connect();

        OutputStream outputStream = channelShell.getOutputStream();//写入该流的数据  都将发送到远程端
        //使用PrintWriter 就是为了使用println 这个方法
        //好处就是不需要每次手动给字符加\n
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println("cd /root");
        printWriter.println("curl https://ahojcn.gitee.io/deploy.sh > deploy.sh");
        printWriter.println("exit");//为了结束本次交互
        printWriter.flush();//把缓冲区的数据强行输出

        byte[] tmp = new byte[1024];
        while(true){

            while(inputStream.available() > 0){
                int i = inputStream.read(tmp, 0, 1024);
                if(i < 0) break;
                String s = new String(tmp, 0, i);
                if(s.indexOf("--More--") >= 0){
                    outputStream.write((" ").getBytes());
                    outputStream.flush();
                }
                System.out.println(s);
            }
            if(channelShell.isClosed()){
                System.out.println("exit-status:"+channelShell.getExitStatus());
                break;
            }
            try{Thread.sleep(1000);}catch(Exception e){}

        }
        outputStream.close();
        inputStream.close();
        channelShell.disconnect();
        session.disconnect();
        System.out.println("DONE");

        return result;
    }

    public static String execCommandSvirt_sandbox_file_t(Session session, String fileAndPath)throws IOException,JSchException{
        String result = "";
        //chcon -Rt svirt_sandbox_file_t fileAndPath
        //2.尝试解决 远程ssh只能执行一句命令的情况
        ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
        InputStream inputStream = channelShell.getInputStream();//从远端到达的数据  都能从这个流读取到
        channelShell.setPty(true);
        channelShell.connect();

        OutputStream outputStream = channelShell.getOutputStream();//写入该流的数据  都将发送到远程端
        //使用PrintWriter 就是为了使用println 这个方法
        //好处就是不需要每次手动给字符加\n
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println("chcon -Rt svirt_sandbox_file_t " + fileAndPath);
        printWriter.println("exit");//为了结束本次交互
        printWriter.flush();//把缓冲区的数据强行输出

        byte[] tmp = new byte[1024];
        while(true){

            while(inputStream.available() > 0){
                int i = inputStream.read(tmp, 0, 1024);
                if(i < 0) break;
                String s = new String(tmp, 0, i);
                if(s.indexOf("--More--") >= 0){
                    outputStream.write((" ").getBytes());
                    outputStream.flush();
                }
                System.out.println(s);
            }
            if(channelShell.isClosed()){
                System.out.println("exit-status:"+channelShell.getExitStatus());
                break;
            }
            try{Thread.sleep(1000);}catch(Exception e){}

        }
        outputStream.close();
        inputStream.close();
        channelShell.disconnect();
        session.disconnect();
        System.out.println("DONE");

        return result;
    }

    public static String execCommandByShellDeleteHost(Session session)throws IOException,JSchException{
        String result = "";
        //2.尝试解决 远程ssh只能执行一句命令的情况
        ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
        InputStream inputStream = channelShell.getInputStream();//从远端到达的数据  都能从这个流读取到
        channelShell.setPty(true);
        channelShell.connect();

        OutputStream outputStream = channelShell.getOutputStream();//写入该流的数据  都将发送到远程端
        //使用PrintWriter 就是为了使用println 这个方法
        //好处就是不需要每次手动给字符加\n
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println("sh clear.sh");
        //printWriter.println("curl https://ahojcn.gitee.io/deploy.sh > deploy.sh");
        printWriter.println("exit");//为了结束本次交互
        printWriter.flush();//把缓冲区的数据强行输出

        byte[] tmp = new byte[1024];
        while(true){

            while(inputStream.available() > 0){
                int i = inputStream.read(tmp, 0, 1024);
                if(i < 0) break;
                String s = new String(tmp, 0, i);
                if(s.indexOf("--More--") >= 0){
                    outputStream.write((" ").getBytes());
                    outputStream.flush();
                }
                System.out.println(s);
            }
            if(channelShell.isClosed()){
                System.out.println("exit-status:"+channelShell.getExitStatus());
                break;
            }
            try{Thread.sleep(1000);}catch(Exception e){}

        }
        outputStream.close();
        inputStream.close();
        channelShell.disconnect();
        session.disconnect();
        System.out.println("DONE");

        return result;
    }
}
