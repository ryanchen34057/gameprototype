package character;

import UI.Game;
import enums.Direction;
import enums.State;
import level.Tile;
import util.Handler;


import java.awt.*;

public class Player extends Entity {

    private int frame;
    private int frameDelay;

    // State
    private boolean isJumping;
    private boolean isFalling;
    private boolean animate;
    private boolean isOnTheGround;

    public static final int normalJumpHeight = 11;

    public Player(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        frameDelay = 0;
        frame = 0;
        isJumping = false;
        isFalling = false;
        animate = false;
        isOnTheGround = false;
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
        setX(getX() + getVelX());
        setY(getY() + getVelY());

        if (getVelX() != 0) animate = true;
        else animate = false;

        boolean didIstepOnTheGround = false;
        Tile t;
        for (int i = 0; i < Game.handler.getTiles().size(); i++) {
            t = Game.handler.getTiles().get(i);
            if (t.getId() == Id.wall) {
                if (checkCollisionTop(t)) {
                    setVelY(0);
                    if (isJumping) {
                        isJumping = false;
                        isFalling = true;
                    }
                }
                if (checkCollisionBottom(t)) {
                    setVelY(0);
                    isOnTheGround = true;
                    if(isFalling) {
                        isFalling = false;
                    }
                    didIstepOnTheGround = true;
                }
                if (checkCollisionLeft(t)) {
                    setVelX(0);
                    setX(t.getX() + t.getWidth());
                }
                if (checkCollisionRight(t)) {
                    setVelX(0);
                    setX(t.getX() - t.getWidth());
                }
            }
        }

        //Check if on the ground
        if(!didIstepOnTheGround) {
            isOnTheGround = false;
        }
        if(!isOnTheGround) {
            if(!isFalling && !isJumping) {
                setGravity(0.5);
                isFalling = true;
                isOnTheGround = false;
            }
        }

        if (isJumping) {
            setGravity(getGravity() - 0.3);
            setVelY((int) -getGravity());
            if (getGravity() <= 0.0) {
                isJumping = false;
                isFalling = true;
            }
        }
        if (isFalling) {
            setGravity(getGravity() + 0.2);
            setVelY((int) getGravity());
        }
        if (animate && !isJumping && !isFalling) {
            frameDelay++;
            if (frameDelay >= 3) {
                frame++;
                if (frame >= Game.getPlayerMoveFrame().length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public boolean isAnimate() {
        return animate;
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    public boolean isOnTheGround() {
        return isOnTheGround;
    }

    public void setOnTheGround(boolean onTheGround) {
        isOnTheGround = onTheGround;
    }

    private boolean checkCollisionTop(Tile t) {
        return getBoundsTop().intersects(t.getBounds());
    }
    private boolean checkCollisionBottom(Tile t) {
        return getBoundsBottom().intersects(t.getBounds());
    }
    private boolean checkCollisionLeft(Tile t) {
        return getBoundsLeft().intersects(t.getBounds());
    }
    private boolean checkCollisionRight(Tile t) {
        return getBoundsRight().intersects(t.getBounds());
    }

}