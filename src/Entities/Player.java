package Entities;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Player extends JLabel implements Runnable {
    private final JFrame frame;
    private final float speed;
    private final int fireRate;
    private final int diagonalSpeed;

    private int vectorX;
    private int vectorY;
    private double angle;
    private boolean shooting;
    private int framesSinceLastShoot;

    private final static int[][] angles = {{0, 270}, {0, 0, 180}, {0, 90}}; // Constant

    public Player(JFrame frame, float speed, float fireRate) {
        this.frame = frame;
        this.speed = speed;
        this.fireRate = (int) (60 / fireRate);
        this.framesSinceLastShoot = this.fireRate;
        this.diagonalSpeed = (int) Math.sqrt(Math.pow(speed, 2) / 2);
        this.setBounds(frame.getSize().width / 2 - 50, frame.getSize().height / 2 - 50, 100, 100);

        Thread playerThread = new Thread(this);
        playerThread.start();

        frame.getContentPane().add(this);
    }

    public void setKeysPressed(ArrayList<Integer> keysPressed) {
        vectorX = 0;
        vectorY = 0;
        shooting = false;
        if (!keysPressed.isEmpty()) { // Keybindings
            for (int keyCode: keysPressed) {
                switch (keyCode) {
                    case 37: case 65: vectorX -= speed; break; // Up (W)
                    case 38: case 87: vectorY -= speed; break; // Right (D)
                    case 39: case 68: vectorX += speed; break; // Down (S)
                    case 40: case 83: vectorY += speed; break; // Left (A)
                    case 32: shooting = true; break; // Shoot (Space)
                }
            }

            if (Math.abs(vectorX) == Math.abs(vectorY) && vectorX != 0) { // Normalises the movement vectors
                vectorX = diagonalSpeed * vectorX / Math.abs(vectorX);
                vectorY = diagonalSpeed * vectorY / Math.abs(vectorY);
            }

            if (Math.abs(vectorX) != Math.abs(vectorY)) { // Calculates the player angle
                int x = 1;
                int y = 1;
                if (vectorX != 0) { x = vectorX / Math.abs(vectorX) + 1; }
                if (vectorY != 0) { y = vectorY / Math.abs(vectorY) + 1; }
                angle = Math.toRadians(angles[x][y]);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) { // Orientates the player
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.rotate(angle, 50, 50);
        graphics2D.drawImage(new ImageIcon("resources/player.png").getImage(), 0, 0, null);
    }

    @Override
    public void run() { // Player clock (runs on separate thread to not interfere with input)
        while (true) {
            int vectorXWithBorderCollision = vectorX; // Frame border collision
            int vectorYWithBorderCollision = vectorY;
            if (this.getX()+this.getWidth() > frame.getContentPane().getWidth()) {
                vectorXWithBorderCollision = -1;
            }
            if (this.getX() < frame.getContentPane().getX()) {
                vectorXWithBorderCollision = 1;
            }
            if (this.getY()+this.getHeight() > frame.getContentPane().getHeight()) {
                vectorYWithBorderCollision = -1;
            }
            if (this.getY() < frame.getContentPane().getY()) {
                vectorYWithBorderCollision = 1;
            }

            if (vectorX != 0 || vectorY != 0) { // Movement
                this.setLocation(this.getX() + vectorXWithBorderCollision, this.getY() + vectorYWithBorderCollision);
            }


            if (shooting && framesSinceLastShoot >= fireRate) { // Shooting
                framesSinceLastShoot = 0;
                frame.getContentPane().add(new Projectile(frame, angle, this.getBounds(), speed));
            }
            framesSinceLastShoot++;


            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}