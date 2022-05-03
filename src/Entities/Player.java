package Entities;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import Handlers.InputHandler;
import Objects.Projectile;
import UI.GameWindow;
import UI.Obstacle;

public class Player extends JLabel implements Runnable {
    private final GameWindow gameWindow;
    private final float speed;
    private final int fireRate;
    private final int diagonalSpeed;

    private int vectorX;
    private int vectorY;
    private double angle;
    private boolean shooting;
    private int framesSinceLastShoot;

    private final static int[][] angles = {{0, 270}, {0, 0, 180}, {0, 90}}; // Constant

    public Player(GameWindow gameWindow, float speed, float fireRate) {
        this.gameWindow = gameWindow;
        this.speed = speed;
        this.fireRate = (int) (60 / fireRate);
        this.framesSinceLastShoot = this.fireRate;
        this.diagonalSpeed = (int) Math.sqrt(Math.pow(speed, 2) / 2);
        this.setBounds(gameWindow.getSize().width / 2 - 50, gameWindow.getSize().height / 2 - 50, 100, 100);

        new InputHandler(gameWindow, this);

        Thread playerThread = new Thread(this);
        playerThread.start();

        gameWindow.getContentPane().add(this);
    }

    public void setKeysPressed(ArrayList<Integer> keysPressed) {
        vectorX = 0;
        vectorY = 0;
        shooting = false;

        // Keybindings
        if (!keysPressed.isEmpty()) {
            for (int keyCode: keysPressed) {
                switch (keyCode) {
                    case 37: case 65: vectorX -= speed; break; // Up (W)
                    case 38: case 87: vectorY -= speed; break; // Right (D)
                    case 39: case 68: vectorX += speed; break; // Down (S)
                    case 40: case 83: vectorY += speed; break; // Left (A)
                    case 32: shooting = true; break; // Shoot (Space)
                }
            }

            // Normalises the movement vectors
            if (Math.abs(vectorX) == Math.abs(vectorY) && vectorX != 0) {
                vectorX = diagonalSpeed * vectorX / Math.abs(vectorX);
                vectorY = diagonalSpeed * vectorY / Math.abs(vectorY);
            }

            // Calculates the player angle
            if (Math.abs(vectorX) != Math.abs(vectorY)) {
                int x = 1;
                int y = 1;
                if (vectorX != 0) { x = vectorX / Math.abs(vectorX) + 1; }
                if (vectorY != 0) { y = vectorY / Math.abs(vectorY) + 1; }
                angle = Math.toRadians(angles[x][y]);
            }
        }
    }

    // Orientates the player
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.rotate(angle, 50, 50);
        graphics2D.drawImage(new ImageIcon("resources/player.png").getImage(), 0, 0, null);
    }

    // Player clock (runs on separate thread to not interfere with input)
    @Override
    public void run() {
        while (true) {
            int vectorXCollision = vectorX;
            int vectorYCollision = vectorY;

            // Obstacle collision
            for (Component component: gameWindow.getContentPane().getComponents()) {
                if (component instanceof Obstacle && this.getBounds().intersects(component.getBounds())) {
                    vectorXCollision = 0;
                    vectorYCollision = 0;
                }
            }

            // Movement
            if (vectorX != 0 || vectorY != 0) {
                this.setLocation(this.getX() + vectorXCollision, this.getY() + vectorYCollision);
            }

            // Shooting
            if (shooting && framesSinceLastShoot >= fireRate) {
                framesSinceLastShoot = 0;
                gameWindow.getContentPane().add(new Projectile(gameWindow, angle, this.getBounds(), speed));
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