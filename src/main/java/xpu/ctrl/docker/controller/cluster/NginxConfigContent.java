package xpu.ctrl.docker.controller.cluster;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

//Nginx配置文件
public interface NginxConfigContent {
    String content = "user  root;\n" +
            "worker_processes  1;\n" +
            "\n" +
            "events {\n" +
            "    worker_connections  1024;\n" +
            "}\n" +
            "\n" +
            "http {\n" +
            "    include       mime.types;\n" +
            "    default_type  application/octet-stream;\n" +
            "    sendfile        on;\n" +
            "    keepalive_timeout  65;\n" +
            "\n" +
            "    gzip  on;\n" +
            "    gzip_min_length 10k;\n" +
            "    gzip_comp_level 3;\n" +
            "    gzip_types text/plain application/javascript application/x-javascript text/css application/xml text/javascript;\n" +
            "\n" +
            "\tupstream mycluster_server_name{\n" +
            "\t\t#Load balancing strategy\n" +
            "\t\tleast_conn;\n" +
            "\t\tmy_server_ip_and_port_list\n" +
            "\t}\n" +
            "\n" +
            "    server {\n" +
            "        listen       80;\n" +
            "        server_name  localhost;\n" +
            "\n" +
            "        location / {\n" +
            "            proxy_pass http://mycluster_server_name;\n" +
            "            index  index.html index.htm;\n" +
            "        }\n" +
            "\t\t\n" +
            "\terror_page   500 502 503 504  /50x.html;\n" +
            "\tlocation = /50x.html {\n" +
            "\t\troot   html;\n" +
            "\t}\n" +
            "    }\n" +
            "}\n";

    public static void main(String[] args) throws IOException {
        System.out.println(NginxConfigContent.content);
        String initConfigString = NginxConfigContent.content;
        StringBuilder builder = new StringBuilder();
        builder.append("server ").append("192.179.2.2").append(":").append(80).append(";\n");
        initConfigString = initConfigString.replace("my_server_ip_and_port_list", builder.toString());
        initConfigString = initConfigString.replace("my_self_server_port", "8081");
        File file = new File("a.txt");
        FileUtils.writeStringToFile(file, initConfigString);
    }
}
