package character;

import UI.Game;
import graphics.MoveFrameManager;
import input.Input;
import level.Tile;
import states.PlayerState;
import states.StateMachine;
import util.CollisionCondition;


import java.awt.*;
import java.util.Stack;

public class Player extends Entity {

    private int frame;
    private int frameDelay;
    private final int STAMINA = 100;
    public static final int DASH_SPEED = 20;
    public static final float DASH_TIMER = 0.5f;
    public float CURRENT_DASH_SPEED = 20;

    // State
    public  StateMachine currentState;
    public StateMachine prevState;
    private boolean isOnTheGround;
    public int fatigue;

    public Player(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        frameDelay = 0;
        frame = 0;
        isOnTheGround = false;
        prevState = null;
        currentState = PlayerState.standing;
        fatigue = 0;
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
        if(Game.debugMode) {
            g.setColor(Color.BLUE);
            g.drawRect(super.getX(), super.getY(), getWidth(), getHeight());
            g.drawRect(super.getX()+40, super.getY(), getWidth()-80, 1);
            g.drawRect(super.getX()+40, super.getY()+getHeight(), getWidth()-80, 1);
            g.drawRect(super.getX()+25, super.getY()+20, 1, getWidth()-40);
            g.drawRect(super.getX()+getWidth()-30, super.getY()+20,1, getWidth()-40);
        }

    }

    @Override
    public void update() {
        if(prevState != currentState) {
            prevState = currentState;
//            System.out.println(prevState);
        }
        setX(getX() + (int)getVelX());
        setY(getY() + (int)getVelY());
        handleKeyInput();
        currentState.update(this);
        isOnTheGround = false;
        Tile t;
        for (int i = 0; i < Game.handler.getTiles().size(); i++) {
            t = Game.handler.getTiles().get(i);
            //Wall collision test
            if (t.getId() == Id.wall || t.getId() == Id.spike) {
                if (checkCollisionTop(t, Tile::getBounds)) {
                    System.out.println("TOP");
                    setGravity(0.8);
                    setVelY(0);
                    setY(t.getY() + t.getHeight());
                    currentState = PlayerState.falling;

                }
                if (checkCollisionLeft(t, Tile::getBounds)) {
//                    System.out.println("LEFT");
                    setVelX(0);
                    setX(t.getX() + t.getWidth() - 25);
                    if(currentState == PlayerState.falling &&
                            Input.keys.get(2).down && fatigue < STAMINA) {
                        currentState = PlayerState.sliding;
                    }
                }
                if (checkCollisionRight(t, Tile::getBounds)) {
//                    System.out.println("RIGHT");
                    setVelX(0);
                    setX(t.getX() - getWidth() + 28);
                    if(currentState == PlayerState.falling&& Input.keys.get(3).down && fatigue < STAMINA) {
                        currentState = PlayerState.sliding;
                    }
                }
                if (checkCollisionBottom(t, Tile::getBounds)) {
//                    System.out.println("BOTTOM");
                    if(currentState == PlayerState.falling || currentState == PlayerState.sliding) {
                        setVelY(0);
                        setY(t.getY() - getHeight());
                        currentState = PlayerState.standing;
                    }
                    isOnTheGround = true;
                    fatigue = 0;
                    PlayerState.dashingInTheAir.isTired = false;
                }
            }
            // Spike collision test
            if(t.getId() == Id.spike) {
                if (checkCollisionTop(t, Tile::getBoundsTop)) {
                    Game.handler.getEntities().remove(this);
                }
                if (checkCollisionBottom(t, Tile::getBoundsTop)) {
                    Game.handler.getEntities().remove(this);
                }

                if (checkCollisionLeft(t, Tile::getBoundsTop)) {
                    Game.handler.getEntities().remove(this);
                }
                if (checkCollisionRight(t, Tile::getBoundsTop)) {
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
        else if(currentState == PlayerState.dashing) {
            frameDelay++;
            if (frameDelay >= 10) {
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

    public int getSTAMINA() {
        return STAMINA;
    }

    public int getFatigue() {
        return fatigue;
    }

    public int getDashSpeed() {
        return DASH_SPEED;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public void accumulateFatigue() {
        this.fatigue++;
    }

    public void handleKeyInput() {
        currentState.handleKeyInput(this, Input.keys);
    }

    private boolean checkCollisionTop(Tile t, CollisionCondition collisionCondition) {
        return getBoundsTop().intersects(collisionCondition.checkCollision(t));
    }
    private boolean checkCollisionBottom(Tile t, CollisionCondition collisionCondition) {
        return getBoundsBottom().intersects(collisionCondition.checkCollision(t));
    }
    private boolean checkCollisionLeft(Tile t, CollisionCondition collisionCondition) {
        return getBoundsLeft().intersects(collisionCondition.checkCollision(t));
    }
    private boolean checkCollisionRight(Tile t, CollisionCondition collisionCondition) {
        return getBoundsRight().intersects(collisionCondition.checkCollision(t));
    }



    private boolean isOnTheGroundCondition() {
        return !isOnTheGround && currentState != PlayerState.standingJumping && currentState != PlayerState.runningJumping
                && currentState != PlayerState.dashJumping && currentState != PlayerState.falling
                && currentState != PlayerState.standing && currentState != PlayerState.sliding && currentState != PlayerState.bouncing
                && currentState != PlayerState.dashingInTheAir;
    }

}