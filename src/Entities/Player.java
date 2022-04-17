package Entities;

import Handlers.ResourceHandler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends JLabel implements Runnable{
    private ArrayList<Integer> keysPressed;
    private final JFrame frame;
    private final float speed;
    private final int diagonalSpeed;
    private final int sizeX = 100;
    private final int sizeY = 100;

    public Player(JFrame frame, float speed) {
        this.frame = frame;
        this.speed = speed;
        diagonalSpeed = (int) Math.sqrt(Math.pow(speed,2)/2);

        this.setBounds(frame.getSize().width/2-sizeX/2, frame.getSize().height/2-sizeY/2, sizeX, sizeY);
        setDirection("up");

        Thread movementThread = new Thread(this);
        movementThread.start();

        frame.getContentPane().add(this);
    }

    public void SetKeysPressed(ArrayList<Integer> keysPressed) {
        this.keysPressed = keysPressed;
    }


    private void setDirection(String direction) {
        this.setIcon(new ImageIcon(Objects.requireNonNull(ResourceHandler.getPlayerResource(direction))));
        frame.revalidate();
        frame.repaint();
    }

    // Runs on a separate thread
    private void update() {
        while (true) {
            int horizontal = 0;
            int vertical = 0;
            try {
                if (keysPressed!=null && !keysPressed.isEmpty()) {
                    for (int keyCode: keysPressed) {
                        switch (keyCode) {
                            case 65: horizontal-=speed; break;
                            case 87: vertical-=speed; break;
                            case 68: horizontal+=speed; break;
                            case 83: vertical+=speed; break;
                        }
                    }
                }
                if (Math.abs(horizontal) == Math.abs(vertical) && horizontal != 0) {
                    if (horizontal == speed) {
                        horizontal = diagonalSpeed;
                    } else if (horizontal == -speed) {
                        horizontal = -diagonalSpeed;
                    }
                    if (vertical == speed) {
                        vertical = diagonalSpeed;
                    } else if (vertical == -speed) {
                        vertical = -diagonalSpeed;
                    }
                }
                this.setLocation(this.getLocation().x+horizontal, this.getLocation().y+vertical);

                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        update();
    }
}