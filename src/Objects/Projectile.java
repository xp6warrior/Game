package Objects;

import javax.swing.*;
import java.awt.*;

public class Projectile extends JLabel implements Runnable {
    private final JFrame frame;
    private final float speed;
    private int xPos;
    private int yPos;
    private final byte[] direction = {0, 0, 0, 0};

    public Projectile(JFrame frame, double playerAngle, Rectangle playerBounds, float playerSpeed) {
        this.frame = frame;
        this.speed = playerSpeed + 3;
        this.setSize(40, 40);
        this.setIcon(new ImageIcon("resources/projectile.png"));

        xPos = playerBounds.x + playerBounds.width / 2 - 20;
        yPos = playerBounds.y + playerBounds.height / 2 - 20;

        switch ((int) Math.toDegrees(playerAngle)) { // Calculating direction, offsetting projectile
            case 0: direction[0] = 1; yPos -= playerBounds.height / 2; break;
            case 90: direction[1] = 1; xPos += playerBounds.width / 2; break;
            case 180: direction[2] = 1; yPos += playerBounds.height / 2; break;
            case 270: direction[3] = 1; xPos -= playerBounds.width / 2; break;
        }

        Thread shootThread = new Thread(this);
        shootThread.start();
    }

    @Override
    public void run() {
        while(true) {
            xPos += speed * direction[1] + -speed * direction[3]; // Move by multiplying incorrect vectors by 0
            yPos += -speed * direction[0] + speed * direction[2];
            this.setLocation(xPos, yPos);

            if (!this.getBounds().intersects(frame.getContentPane().getBounds())) { break; } // Disable when reaches frame border

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}