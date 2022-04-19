package Handlers;

import Entities.Player;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InputHandler implements KeyListener {
    private final ArrayList<Integer> keysPressed = new ArrayList<>();
    private final Player player;

    public InputHandler(JFrame frame, Player player) {
        frame.addKeyListener(this);
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!keysPressed.contains(e.getKeyCode())) {
            keysPressed.add(e.getKeyCode());
        }
        player.setKeysPressed(keysPressed);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (keysPressed.contains(e.getKeyCode())) {
            keysPressed.remove(new Integer(e.getKeyCode()));
        }
        player.setKeysPressed(keysPressed);
    }
}