package character;

import UI.Game;
import level.Tile;
import util.Handler;

import java.awt.*;

public class Player extends Entity {

    private int frame;
    private int frameDelay;

    public Player(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        frameDelay = 0;
        frame = 0;
    }

    @Override
    public void paint(Graphics g) {
        if (super.getFacing() == 0) {
            g.drawImage(Game.getPlayerMoveFrame()[frame + 4].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        } else {
            g.drawImage(Game.getPlayerMoveFrame()[frame].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }
    }

    @Override
    public void update() {
        super.setX(super.getX() + super.getVelX());
        super.setY(super.getY() + super.getVelY());
        System.out.println(super.getX() + "," + super.getY());

        for(Tile t: Game.handler.getTiles()) {
            if(getBoundsLeft().intersects(t.getBounds())) {
                setVelX(0);
                setX(t.getX() + t.getWidth());
            }
            if(getBoundsRight().intersects(t.getBounds())) {

                setX(t.getX() - t.getWidth());
            }
        }

        //Change facing
        if (isAnimate()) {
            frameDelay++;
            if (frameDelay >= Game.getPlayerMoveFrame().length / 2) {
                frame++;
                if (frame >= Game.getPlayerMoveFrame().length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    }
}