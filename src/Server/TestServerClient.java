package Server;

import Client.CP2Client;

/**
 * Created by nicholas on 21-May-17.
 */
public class TestServerClient {

    public static void main(String[] args) {
        Runnable server = new ServerClassCP2MultiThread();
        Runnable client = new CP2Client();

//        new Thread(server).start();
        new Thread(client).start();
    }
}