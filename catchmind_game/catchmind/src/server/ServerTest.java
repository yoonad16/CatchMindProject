package server;

import java.util.Scanner;

public class ServerTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Server server = new Server();
        server.start();
    }
}
