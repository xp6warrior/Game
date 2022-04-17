package UI;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        this.setSize(1000, 800);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setTitle("Game by xp6warrior!");
        float[] hsb = Color.RGBtoHSB(255, 217, 168, null);
        this.getContentPane().setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        this.setVisible(true);
    }
}