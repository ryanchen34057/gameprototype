package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Input implements KeyListener {

    public static List<Key> keys = new ArrayList<>();

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key x = new Key();
    public Key c = new Key();

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    private void toggle(KeyEvent e, boolean pressed) {
        if (e.getKeyCode() == KeyEvent.VK_UP) up.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_X) x.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_C) c.toggle(pressed);
    }

    public void releaseAll() {
        for (Key key : keys) key.down = false;
    }

    public class Key {
        public boolean down;

        public Key() {
            keys.add(this);
        }

        public void toggle(boolean pressed) {
            if (pressed != down) down = pressed;
        }

        @Override
        public String toString() {
            return down + " ";
        }
    }
}