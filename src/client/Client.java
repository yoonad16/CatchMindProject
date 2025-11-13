package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
 private Socket socket;
 private BufferedReader in;
 private PrintWriter out;

 public void connect(String ip, int port) throws UnknownHostException, IOException {
     socket = new Socket(ip, port);
     in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
     out = new PrintWriter(socket.getOutputStream());
 }

 public void send(String msg) {
     out.println(msg);
     out.flush();
 }

 public void listen() {
     new Thread(() -> {
         String msg;
         try {
			while ((msg = in.readLine()) != null) {
			     System.out.println("서버 메시지: " + msg);
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }).start();
 }
}
