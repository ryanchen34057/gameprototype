package input;

import UI.Game;
import character.Id;
import character.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for(int i=0;i<Game.handler.getEntities().size();i++) {
            Player p;
            if(Game.handler.getEntities().get(i).getId() == Id.player) {
                p = (Player) Game.handler.getEntities().get(i);
                switch (key) {
                    case KeyEvent.VK_W:
                        if(!p.isJumping()) {
                            p.setJumping(true);
                            p.setGravity(8);
                        }
                        break;
                    case KeyEvent.VK_A:
                        p.setVelX(-5);
                        p.setFacing(0);
                        p.setAnimate(true);
                        break;
                    case KeyEvent.VK_D:
                        p.setVelX(5);
                        p.setFacing(1);
                        p.setAnimate(true);
                        break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for(int i=0;i<Game.handler.getEntities().size();i++) {
            Player p;
            if(Game.handler.getEntities().get(i).getId() == Id.player) {
                p = (Player) Game.handler.getEntities().get(i);
                switch (key) {
                    case KeyEvent.VK_W:
                        p.setVelY(0);
                        break;
                    case KeyEvent.VK_A:
                        p.setVelX(0);
                        p.setAnimate(false);
                        break;
                    case KeyEvent.VK_D:
                        p.setVelX(0);
                        p.setAnimate(false);
                        break;
                }
            }
        }
    }

}
