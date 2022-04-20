package Entities;

import javax.swing.*;
import java.awt.*;

public class Projectile extends JLabel implements Runnable {
    private final JFrame frame;
    private final float speed;
    private int xPos;
    private int yPos;
    private final byte[] direction = {0, 0, 0, 0};

    public Projectile(double angle, Rectangle rect, float speed, JFrame frame) {
        this.frame = frame;
        this.speed = speed + 3;
        this.setIcon(new ImageIcon("resources/projectile.png"));

        xPos = rect.x + (rect.width / 2) / 2;
        yPos = rect.y + (rect.height / 2) / 2;

        switch ((int) Math.toDegrees(angle)) {
            case 0: direction[0] = 1; yPos -= rect.height / 2; break;
            case 90: direction[1] = 1; xPos += rect.width / 2; break;
            case 180: direction[2] = 1; yPos += rect.height / 2; break;
            case 270: direction[3] = 1; xPos -= rect.width / 2; break;
        }

        Thread shootThread = new Thread(this);
        shootThread.start();
    }

    @Override
    public void run() {
        while(true) {
            xPos += speed * direction[1] + -speed * direction[3];
            yPos += -speed * direction[0] + speed * direction[2];
            this.setBounds(xPos, yPos, 50, 50);

            if (Math.abs(xPos) > frame.getWidth() || Math.abs(yPos) > frame.getHeight()) {break;}

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}