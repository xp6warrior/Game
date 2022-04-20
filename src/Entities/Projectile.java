package Entities;

import javax.swing.*;
import java.awt.*;

public class Projectile extends JPanel implements Runnable {
    private int xPos;
    private int yPos;
    Rectangle rect;
    double angle;
    JFrame frame;
    float speed;

    public Projectile(double angle, Rectangle rect, float speed, JFrame frame) {
        this.rect = rect;
        this.angle = angle;
        this.frame = frame;
        this.speed = speed + 5;

        xPos = rect.x + (rect.width / 2) / 2;
        yPos = rect.y + (rect.height / 2) / 2;

        this.setBackground(Color.gray);
        frame.getContentPane().add(this);

        Thread shootThread = new Thread(this);
        shootThread.start();
    }

    @Override
    public void run() {
        while(true) {
            switch ((int) Math.toDegrees(angle)) {
                case 0: yPos -= speed; break;
                case 90: xPos += speed; break;
                case 180: yPos += speed; break;
                case 270: xPos -= speed; break;
            }
            this.setBounds(xPos, yPos, 50, 50);

            if (Math.abs(xPos) > frame.getWidth() || Math.abs(yPos) > frame.getHeight()) {
                break;
            }

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}