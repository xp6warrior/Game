package Entities;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Player extends JLabel implements Runnable {
    private final float speed;
    private final int diagonalSpeed;
    private int vectorX;
    private int vectorY;
    private double angle;

    private final static int[][] angles = {{0, 270}, {0, 0, 180}, {0, 90}};

    public Player(JFrame frame, float speed) {
        this.speed = speed;
        this.diagonalSpeed = (int) Math.sqrt(Math.pow(speed, 2) / 2);
        this.setBounds(frame.getSize().width / 2 - 50, frame.getSize().height / 2 - 50, 100, 100);

        Thread movementThread = new Thread(this);
        movementThread.start();

        frame.getContentPane().add(this);
    }

    public void setKeysPressed(ArrayList<Integer> keysPressed) {
        vectorX = 0;
        vectorY = 0;
        if (!keysPressed.isEmpty()) {
            for (int keyCode: keysPressed) { // Calculates speed for each vector
                switch (keyCode) {
                    case 65: vectorX -= speed; break;
                    case 87: vectorY -= speed; break;
                    case 68: vectorX += speed; break;
                    case 83: vectorY += speed; break;
                }
            }
            if (Math.abs(vectorX) == Math.abs(vectorY) && vectorX != 0) { // Normalises the movement vectors
                vectorX = diagonalSpeed * vectorX / Math.abs(vectorX);
                vectorY = diagonalSpeed * vectorY / Math.abs(vectorY);
            }

            if (Math.abs(vectorX) != Math.abs(vectorY)) { // Calculates the player angle
                int x = 1;
                int y = 1;
                if (vectorX != 0) {
                    x = vectorX / Math.abs(vectorX) + 1;
                }
                if (vectorY != 0) {
                    y = vectorY / Math.abs(vectorY) + 1;
                }
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

    private void Update() { // Movement clock (runs on separate thread to not interfere with input)
        while (true) {
            try {
                if (vectorX != 0 || vectorY != 0) {
                    this.setLocation(this.getLocation().x + vectorX, this.getLocation().y + vectorY);
                }
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
    @Override
    public void run() {
        Update();
    }
}