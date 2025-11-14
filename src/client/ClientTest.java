package client;

import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Client client = new Client();
        client.connect("127.0.0.1",50023);
        client.listen();

        String msg;
        while (true) {
            if(sc.hasNext()) {
                msg = sc.nextLine();
                client.send(msg);
            }
        }
    }
}
