package character;

import UI.Game;
import input.Input;
import level.Tile;


import java.awt.*;

public class Player extends Entity {

    private int frame;
    private int frameDelay;
    private int dashSpeed;
    private float dashTime;
    private float startDashTime;

    // State
    private boolean isJumping;
    private boolean isFalling;
    private boolean animate;
    private boolean isOnTheGround;
    private boolean dash;
    private boolean isTired;

    public static final int normalJumpHeight = 11;

    public Player(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        frameDelay = 0;
        frame = 0;
        isJumping = false;
        isFalling = false;
        animate = false;
        isOnTheGround = false;
        dash = false;
        dashSpeed = 10;
        isTired = false;
        startDashTime = 0.1f;
        dashTime = startDashTime;
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
        handleKeyInput();
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

//        if(dash) {
//            if(dashTime > 0) {
//                dashTime -= 0.001;
//                if(getFacing() == 0) {
//                    setVelX(getVelX()-dashSpeed);
//                }
//                else {
//                    setVelX(getVelX() + dashSpeed);
//                }
//            }
//            else {
//                dashTime = startDashTime;
//                dash = false;
//            }
//
//        }

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

    public void handleKeyInput() {
        // up, down, left, right, x, c
        // X Key(Dash)
        if(Input.keys.get(4).down) {
            if(!dash) {
                dash = true;
            }
        }
        // C Key(jump)
        if (Input.keys.get(5).down && Input.keys.get(2).down) {
            if (!isJumping() && isOnTheGround() && !isFalling()) {
                setOnTheGround(false);
                setJumping(true);
                setGravity(Player.normalJumpHeight);
                setVelX(-getStep()-2);
                setFacing(0);
            }
        }
        else if (Input.keys.get(5).down && Input.keys.get(3).down) {
            if (!isJumping() && isOnTheGround() && !isFalling()) {
                setOnTheGround(false);
                setJumping(true);
                setGravity(Player.normalJumpHeight);
                setVelX(getStep()+2);
                setFacing(1);
            }
        }
        else if ((Input.keys.get(5).down)) {
            if (!isJumping() && isOnTheGround() && !isFalling()) {
                setOnTheGround(false);
                setJumping(true);
                setGravity(Player.normalJumpHeight);
            }
        }
        // left Key
        else if (Input.keys.get(2).down) {
            setVelX(-getStep());
            setFacing(0);
        }
        // right Key
        else if (Input.keys.get(3).down) {
            setVelX(getStep());
            setFacing(1);
        }

        // If no key was pressed
        else {
            setVelX(0);
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