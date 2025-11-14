package client;

import javax.swing.text.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
 private Socket socket;
 private Scanner in;
 private PrintWriter out;
 private ViewController viewController;

 //IP주소랑 포트번호 입력해야함 (나중에 View에서 입력받아도 좋을듯)
 public void connect(String ip, int port){
     try {
         socket = new Socket(ip, port);
         in = new Scanner(socket.getInputStream());
         out = new PrintWriter(socket.getOutputStream());
     } catch (IOException e) {
         e.printStackTrace();
     }

 }

 public void send(String msg) {
     out.println(msg);
     out.flush();
 }

 public void listen() {
     Thread listen = new Thread(new Runnable() {
         @Override
         public void run() {
             String msg;
             while (in.hasNext()) {
                 msg = in.nextLine();
                 System.out.println(msg);
                 viewController.updateChatPanel(msg);
             }
         }
     });
     listen.start();
 }

 public void setViewController(ViewController viewController) {
     this.viewController = viewController;
 }
}
