package Server;

import java.net.*;

/**
 * Created by nicholas on 21-May-17.
 */
//public class TestServer {
//    public static void main(String[] args) throws Exception {
//        System.out.println("Server started ...");
//        int portNum = 3451;
//        ServerSocket s = new ServerSocket(portNum);
//        Socket temp = s.accept();
//        System.out.println("Client connected");
//    }
//}

////code for server
//import java.io.*;
//import java.net.*;
//
//public class TestServer
//{
//    ServerSocket ss;
//    Socket s;
//    DataInputStream dis;
//    DataOutputStream dos;
//    public TestServer()
//    {
//        try
//        {
//            System.out.println("Server Started");
//            ss=new ServerSocket(10);
//            s=ss.accept();
//            System.out.println(s);
//            System.out.println("CLIENT CONNECTED");
//            dis= new DataInputStream(s.getInputStream());
//            dos= new DataOutputStream(s.getOutputStream());
//            ServerChat();
//        }
//        catch(Exception e)
//        {
//            System.out.println(e);
//        }
//    }
//
//    public static void main (String as[])
//    {
//        new TestServer();
//    }
//
//    public void ServerChat() throws IOException
//    {
//        String str, s1;
//        do
//        {
//            str=dis.readUTF();
//            System.out.println("Client Message:"+str);
//            BufferedReader br=new BufferedReader(new   InputStreamReader(System.in));
//            s1=br.readLine();
//            dos.writeUTF(s1);
//            dos.flush();
//        }
//        while(!s1.equals("bye"));
//    }
//}

public class TestServer{
    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
//        SocketAddress SA = new InetSocketAddress("10.143.9.169", 3451);
//        Socket s = new Socket();
//        s.connect(SA,8080);
        // Client
//        String hostname = "10.143.9.169";
//        Socket s = new Socket(hostname,6667);
//        System.out.println("Connected");
//        String hostname = "192.168.1.41";
//        Socket s = new Socket(hostname,7777);
//        System.out.println("Connected");

        // Server
//        ServerSocket s = new ServerSocket(6667);
//        s.accept();
        String nullString = null;
        while (NetworkInterface.getNetworkInterfaces().hasMoreElements()){
            System.out.println(NetworkInterface.getNetworkInterfaces().nextElement());
        }

    }
}
