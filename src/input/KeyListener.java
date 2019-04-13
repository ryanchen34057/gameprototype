package input;

import UI.Game;
import character.Entity;
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
            Player player = (Player) Game.handler.getEntities().get(i);
            if(player.getId() == Id.player) {
                switch (key) {
                    case KeyEvent.VK_C:
                        if(!player.isJumping() && player.isOnTheGround() && !player.isFalling()) {
                            player.setOnTheGround(false);
                            player.setJumping(true);
                            player.setGravity(Player.normalJumpHeight);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if(player.getVelY() != 0) {
                            player.setVelX(-2);
                        }
                        else {
                            player.setVelX(-5);
                        }
                        player.setFacing(0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(player.getVelY() != 0) {
                            player.setVelX(2);
                        }
                        else {
                            player.setVelX(5);
                        }
                        player.setFacing(1);
                        break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for(Entity en: Game.handler.getEntities()) {
            if(en.getId() == Id.player) {
                switch (key) {
//                    case KeyEvent.VK_SPACE:
//                        en.setVelY(0);
//                        break;
//                    case KeyEvent.VK_S:
//                        en.setVelY(0);
//                        break;
                    case KeyEvent.VK_LEFT:
                        en.setVelX(0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        en.setVelX(0);
                        break;
                }
            }
        }
    }

}
