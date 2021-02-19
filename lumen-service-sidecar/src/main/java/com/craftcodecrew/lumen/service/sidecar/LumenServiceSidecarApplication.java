package com.craftcodecrew.lumen.service.sidecar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;
import com.jcraft.jsch.*;
import java.util.Properties;
import java.io.*;

@SpringBootApplication
@EnableSidecar
public class LumenServiceSidecarApplication {


public static void go() 
{

 String Password = "Password$2020.";
 int LOCAl_PORT = 5902; 
int REMOTE_PORT = 9700; 
int SSH_REMOTE_PORT = 22; 
String SSH_USER = "ms_edge";
 String SSH_REMOTE_SERVER = "201.159.223.162";
 String MYSQL_REMOTE_SERVER = "127.0.0.1";
 Session sesion;

try{

    	JSch jsch = null;

        jsch = new JSch();
Properties config = new Properties();
config.put("StrictHostKeyChecking", "no");

        sesion = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);
        sesion.setPassword(Password);
	sesion.setConfig(config);
	sesion.connect(); //ssh connection established!



ChannelSftp channelSftp = (ChannelSftp) sesion.openChannel("sftp");
     channelSftp.connect();


 
    String localFile = "C:\\Git-Microservicios Edge-Temonet\\temonet\\storage\\app\\usuario";
    String remoteDir = "/home/ms_edge/lumen/usuario";

String local = "C:\\Git-Microservicios Edge-Temonet\\temonet\\storage\\app\\terapias";
String remote = "/home/ms_edge/lumen/terapias";


    channelSftp.put(localFile, remoteDir);
    channelSftp.put(local, remote);



    channelSftp.exit();



        //by security policy, you must connect through a fowarded port          
        sesion.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT); 
}
catch(Exception e){System.err.print(e);}
}



public static void phpConexion()  throws IOException
{

       String cmd = "cmd.exe /c cd \"C:\\Git-Microservicios Edge-Temonet\\temonet\" && php artisan serve --host=0.0.0.0 --port=8000";
          


//String cmd1=  "php artisan serve --host=0.0.0.0 --port=8000";
	
        Process proc = Runtime.getRuntime().exec(cmd );
	
}



    public static void main(String[] args) {
        SpringApplication.run(LumenServiceSidecarApplication.class, args);
 
try{
            go();
	phpConexion();
        } catch(Exception ex){
            ex.printStackTrace();
        }


    }






}



