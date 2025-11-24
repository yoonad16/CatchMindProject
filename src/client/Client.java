package client;

import client.controller.GameController;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

//실제 통신 담당(서버로 메시지 보내고, 받는 역할)
public class Client {
 private Socket socket;
 private BufferedReader in;
 private PrintWriter out;
 private GameController gameController;

 //송수신
 public void send(String msg) {
     out.println(msg);
     out.flush();
 }
 public void listen() {
     Thread listen = new Thread(() -> {
         String msg;
         try {
             while ((msg = in.readLine())!=null) {
                 System.out.println("[DEBUG] 클라이언트 수신함: " + msg);
                 gameController.processMessage(msg);
             }
         } catch (IOException e) {

         }finally {
             disconnect();
         }
     });

     listen.start();
 }


    //연결
    public void connect(String ip, int port){
        try {
            socket = new Socket(ip, port);
            InputStreamReader input = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
            in = new BufferedReader(input);
            OutputStreamWriter output = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            out = new PrintWriter(output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 public void setGameController(GameController gameController) {this.gameController = gameController;}

}
