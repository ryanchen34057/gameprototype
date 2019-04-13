package character;

import UI.Game;
import enums.Direction;
import enums.State;
import level.Tile;


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

        System.out.println(getPrevioudState() + " " + getCurrentState());

        Direction hCollision, vCollision;
        hCollision = horizontalCollision();
        vCollision = verticalCollision();
        if(vCollision == null && getCurrentState() == State.RUNNING) {
            setPrevioudState(getCurrentState());
            setCurrentState(State.FALLING);
            setGravity(0.6);
        }

        checkJumping();
        checkFalling();

        //Change facing
        if (getCurrentState() == State.RUNNING) {
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

    private Direction horizontalCollision() {
        Tile t;
        for (int i = 0; i < Game.handler.getTiles().size(); i++) {
            t = Game.handler.getTiles().get(i);
            if (getBoundsLeft().intersects(t.getBounds())) {
                setVelX(0);
                setX(t.getX() + t.getWidth());
                return Direction.LEFT;
            }
            if (getBoundsRight().intersects(t.getBounds())) {
                setVelX(0);
                setX(t.getX() - t.getWidth());
                return Direction.RIGHT;
            }
        }
        return null;
    }

    private Direction verticalCollision() {
        Tile t;
        for (int i = 0; i < Game.handler.getTiles().size(); i++) {
            t = Game.handler.getTiles().get(i);
            if (getBoundsBottom().intersects(t.getBounds())) {
                setVelY(0);
                setY(t.getY() - t.getHeight());
                setPrevioudState(getCurrentState());
                setCurrentState(State.STANDING);
                return Direction.DOWN;
            }
            if (getBoundsTop().intersects(t.getBounds())) {
                setY(t.getY() + t.getHeight());
                if (getCurrentState() == State.JUMPING) {
                    setPrevioudState(State.JUMPING);
                    setCurrentState(State.FALLING);
                    setGravity(0.6);
                }
                return Direction.UP;
            }
        }
        return null;
    }

    private void checkJumping() {
        if(getCurrentState() == State.JUMPING) {
            setGravity(getGravity() - 0.1);
            setVelY((int) -getGravity());
            if (getGravity() <= 0) {
                setPrevioudState(State.JUMPING);
                setCurrentState(State.FALLING);
            }
        }
    }

    private void checkFalling() {
        if(getCurrentState() == State.FALLING) {
            setGravity(getGravity() + 0.1);
            setVelY((int) getGravity());
        }
    }
}