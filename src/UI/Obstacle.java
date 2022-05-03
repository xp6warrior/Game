package UI;

import javax.swing.*;
import java.awt.*;

public class Obstacle extends JLabel {
    public Obstacle(Rectangle bounds) {
        this.setBackground(Color.blue);
        this.setBounds(bounds);
        this.setOpaque(true);
    }
}
