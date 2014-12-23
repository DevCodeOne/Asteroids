package DevCodeOne.Input;

import DevCodeOne.GameMechanics.GameClock;
import DevCodeOne.GameMechanics.Tick;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener, Tick {

    private boolean keys[] = new boolean[256];
    private KeyInput input;

    public KeyboardHandler(KeyInput input, GameClock clock, Component component) {
        this.input = input;
        clock.add(this);
        component.addKeyListener(this);
    }

    public void tick() {
        input.handleKeys(keys);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = false;
    }
}
