package client.controller;

import client.ui.MainFrame;
import java.awt.*;

//중계자: 클라이언트-UI 연결하고 중계
public class ViewController {
    private MainFrame mainFrame;

    public void updateDrawState (boolean state) {
        if (state) {
            mainFrame.enableDrawing();
            mainFrame.disableChatting();
        }
        else {
            mainFrame.disableDrawing();
            mainFrame.enableChatting();
        }
    }

    //게임 시작-종료 시 화면 세팅
    public void startPanel() {
        this.mainFrame.enablePanel();
        this.mainFrame.disableStartButton();
    }
    public void endPanel() {
        this.mainFrame.dispose();
    }

    //화면 업데이트
    public void updateKeyWord(String keyword) {
        mainFrame.updateKeyWord(keyword);
    }
    public void updateCanvasPanel(Point from, Point to) {mainFrame.updateCanvas(from, to);}
    public void eraseCanvasPanel() {mainFrame.eraseCanvas();}
    public void updateChatPanel(String msg) {
        mainFrame.updateTextArea(msg);
    }
    public void updateTimer(int time) {
        mainFrame.updateTimer(time);
    }

    public void updateCurrentColor(String colorCode) {mainFrame.updateCurrentColor(colorCode);}

    //Setter
    public void setMainFrame (MainFrame mainFrame) {this.mainFrame = mainFrame;}

}
