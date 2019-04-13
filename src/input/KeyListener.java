package input;

import UI.Game;
import character.Id;
import character.Player;
import enums.State;

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
                    case KeyEvent.VK_SPACE:
                        if(p.getCurrentState() == State.RUNNING || p.getCurrentState() == State.STANDING) {
                            p.setPrevioudState(p.getPrevioudState());
                            p.setCurrentState(State.JUMPING);
                            p.setGravity(7);
                        }
                        break;
                    case KeyEvent.VK_A:
                        p.setVelX(-5);
                        p.setPrevioudState(p.getPrevioudState());
                        p.setCurrentState(State.RUNNING);
                        p.setFacing(0);
                        break;
                    case KeyEvent.VK_D:
                        p.setVelX(5);
                        p.setPrevioudState(p.getPrevioudState());
                        p.setCurrentState(State.RUNNING);
                        p.setFacing(1);
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
                    case KeyEvent.VK_SPACE:
                        p.setVelY(0);
                        break;
                    case KeyEvent.VK_A:
                        p.setVelX(0);
                        p.setPrevioudState(p.getPrevioudState());
                        p.setCurrentState(State.STANDING);
                        break;
                    case KeyEvent.VK_D:
                        p.setVelX(0);
                        p.setPrevioudState(p.getPrevioudState());
                        p.setCurrentState(State.STANDING);
                        break;
                }
            }
        }
    }

}
