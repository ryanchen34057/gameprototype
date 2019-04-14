package character;

import UI.Game;
import graphics.MoveFrameManager;
import input.Input;
import level.Tile;
import states.PlayerState;
import states.StateMachine;
import util.Handler;


import java.awt.*;
import java.util.Stack;

public class Player extends Entity {

    private int frame;
    private int frameDelay;
    private int dashSpeed;
    private float dashTime;
    private float startDashTime;

    // State
    public static Stack<StateMachine> stateStack;
    public  StateMachine currentState;
    public StateMachine prevState;
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
        stateStack = new Stack<>();
        prevState = null;
        currentState = PlayerState.standing;
        stateStack.push(currentState);
    }

    @Override
    public void paint(Graphics g) {
        if (super.getFacing() == -1) {
            g.drawImage(MoveFrameManager.getPlayerMoveFrame(currentState)[frame + 4].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        } else {
            g.drawImage(MoveFrameManager.getPlayerMoveFrame(currentState)[frame].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }
        g.setColor(Color.BLUE);
        g.drawRect(super.getX(), super.getY(), getWidth(), getHeight());
        g.drawRect(super.getX()+40, super.getY(), getWidth()-80, 1);
        g.drawRect(super.getX()+40, super.getY()+getHeight(), getWidth()-80, 1);
        g.drawRect(super.getX()+25, super.getY()+20, 1, getWidth()-40);
        g.drawRect(super.getX()+getWidth()-25, super.getY()+20,1, getWidth()-40);
    }

    @Override
    public void update() {
        if(prevState != currentState) {
            prevState = currentState;
            System.out.println(prevState);
        }
        setX(getX() + (int)getVelX());
        setY(getY() + (int)getVelY());
        handleKeyInput();
        currentState.update(this);

        isOnTheGround = false;
        Tile t;
        for (int i = 0; i < Game.handler.getTiles().size(); i++) {
            t = Game.handler.getTiles().get(i);
            if (t.getId() == Id.wall) {
                if (checkCollisionTop(t)) {
                    if(currentState == PlayerState.standingJumping || currentState == PlayerState.runningJumping) {
                        setGravity(0.8);
                        setVelY(0);
                        setY(t.getY() + getHeight());
                        currentState = PlayerState.falling;
                    }

                }
                if (checkCollisionBottom(t)) {
                    if(currentState == PlayerState.falling) {
                        setVelY(0);
                        setY(t.getY() - getHeight());
                        currentState = PlayerState.standing;
                    }
                    isOnTheGround = true;
                }

                if (checkCollisionLeft(t)) {
                    setVelX(0);
                    setX(t.getX() + t.getWidth() - 25);
                    if(currentState == PlayerState.falling && Input.keys.get(2).down) {
                        currentState = PlayerState.sliding;
                    }
                }
                if (checkCollisionRight(t)) {
                    setVelX(0);
                    setX(t.getX() - getWidth() + 25);
                    if(currentState == PlayerState.falling && Input.keys.get(3).down) {
                        currentState = PlayerState.sliding;
                    }
                }
            }
            if(t.getId() == Id.spike) {
                if (checkCollisionTop(t)) {
                    Game.handler.getEntities().remove(this);
                }
                if (checkCollisionBottom(t)) {
                    Game.handler.getEntities().remove(this);
                }

                if (checkCollisionLeft(t)) {
                    Game.handler.getEntities().remove(this);
                }
                if (checkCollisionRight(t)) {
                    Game.handler.getEntities().remove(this);
                }
            }
        }
        //Check if on the ground
        if(isOnTheGroundCondition()) {
            currentState = PlayerState.falling;
        }

        if(currentState == PlayerState.standing) {
            frameDelay++;
            if (frameDelay >= 30) {
                frame++;
                if (frame >= Game.getPlayerMoveFrame().length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
        else {
            frameDelay++;
            if (frameDelay >= 5) {
                frame++;
                if (frame >= Game.getPlayerMoveFrame().length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }

    }

    public void handleKeyInput() {
        currentState.handleKeyInput(this, Input.keys);
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

    private boolean isOnTheGroundCondition() {
        return !isOnTheGround && currentState != PlayerState.standingJumping && currentState != PlayerState.runningJumping
                && currentState != PlayerState.dashJumping && currentState != PlayerState.falling
                && currentState != PlayerState.standing && currentState != PlayerState.sliding && currentState != PlayerState.bouncing;
    }

}