package Main;

import GUI.Frame;
import GUI.MenuPanel;

public class MainClient {
    public static void main(String[] args) {
        Frame frame = new Frame("Chess Game");
        frame.setIcon("res/Icons/frame_icon.png");

        new MenuPanel(frame);
    }
}
