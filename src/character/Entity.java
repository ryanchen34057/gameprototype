package character;

import java.awt.*;

public abstract class Entity {
    //Coordinate
    private int x;
    private int y;
    private int width;
    private int height;
    private int velX;
    private int velY;
    private int facing;  //0 -> left, 1 -> right

    //Physics
    private double gravity;
    private double friction;

    //State
    private boolean animate;
    private boolean isJumping;
    private boolean isFalling;

    //info
    private Id id;

    public Entity(int x, int y, int width, int height, Id id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velX = 0;
        this.velY = 0;
        gravity = 0;
        isFalling = false;
        isJumping = false;
        this.id = id;
        facing = 0;
        animate = false;
    }

    //getters and setters
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getVelX() {
        return velX;
    }
    public void setVelX(int velX) {
        this.velX = velX;
    }
    public int getVelY() {
        return velY;
    }
    public void setVelY(int velY) {
        this.velY = velY;
    }
    public int getFacing() {
        return facing;
    }
    public Id getId() {
        return id;
    }
    public boolean isJumping() { return isJumping; }
    public boolean isFalling() {
        return isFalling;
    }
    public void setJumping(boolean state) { isJumping = state; }
    public void setFalling(boolean state) { isFalling = state; }
    public double getGravity() {
        return gravity;
    }
    public void setGravity(double gravity) {
        this.gravity += gravity;
    }
    public void setFacing(int facing) {
        this.facing = facing;
    }
    public boolean isAnimate() {
        return animate;
    }
    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    // Drawing method
    public abstract void paint(Graphics g);

    // Update method
    public abstract void update();

    // Collision test
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), width, height);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle(getX()+20, getY(), width-40,5 );
    }
    public Rectangle getBoundsBottom() {
        return new Rectangle(getX()+20, getY()+height-5, width-40,5 );
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle(getX(), getY()+20, 5,height-40 );
    }
    public Rectangle getBoundsRight() {
        return new Rectangle(getX()+width-5, getY()+20, 5,height-40 );
    }

}