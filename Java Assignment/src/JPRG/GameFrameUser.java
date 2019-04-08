package JPRG;

import javax.swing.*;

public class GameFrameUser{

    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        frame.setTitle("JPRG Assignment");
        frame.setSize(1150, 660);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
