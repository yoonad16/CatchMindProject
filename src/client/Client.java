package client;

import client.controller.ViewController;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

//실제 통신 담당(서버로 메시지 보내고, 받는 역할)
public class Client {
 private Socket socket;
 private BufferedReader in;
 private PrintWriter out;
 private ViewController viewController;


 //IP주소랑 포트번호 입력해야함 (나중에 View에서 입력받아도 좋을듯)
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
 public void send(String msg) {
     out.println(msg);
     out.flush();
 }

 public void sendDrawing(Point from, Point to) {
     String msg = String.format("DRAW:%d:%d:%d:%d", from.x, from.y, to.x, to.y);
     out.println(msg);
 }

 public void listen() {
     Thread listen = new Thread(() -> {
         String msg;
         try {
             while ((msg = in.readLine())!=null) {
                 String[] tokens = msg.split(":");
                 if(tokens[0].equals("DRAW")){
                     Point from = new Point(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]));
                     Point to = new Point(Integer.parseInt(tokens[3]),Integer.parseInt(tokens[4]));
                     viewController.updateCanvasPanel(from, to);
                 }
                 else if (tokens[0].equals("ERASE")){
                     viewController.eraseCanvasPanel();
                 }
                 else if (tokens[0].equals("CHAT")) {
                     msg = tokens[1];
                     viewController.updateChatPanel(msg);
                 }
                 else if (tokens[0].equals("DRAWSTATE")) {
                     boolean drawState;
                     if (tokens[1].equals("true")) drawState = true;
                     else drawState = false;

                     viewController.updateDrawState(drawState);

                 }
             }
         } catch (IOException e) {

         }finally {
             disconnect();
         }
     });

     listen.start();
 }

 public void parseMessage(String msg) {
     String[] tokens = msg.split(":");

     switch(tokens[0]) {
         case "DRAW":
             Point from = new Point(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]));
             Point to = new Point(Integer.parseInt(tokens[3]),Integer.parseInt(tokens[4]));
             viewController.updateCanvasPanel(from, to);
             break;
         case "ERASE":
             viewController.eraseCanvasPanel();
             break;
         case "CHAT":
             msg = tokens[2];
             viewController.updateChatPanel(msg);
             break;
     }
 }

 public void setViewController(ViewController viewController) {
     this.viewController = viewController;
 }

}
