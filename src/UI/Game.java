package UI;

import character.Id;
import graphics.Sprite;
import graphics.SpriteSheet;
import input.Input;
import util.Camera;
import util.Handler;
import util.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    private static final String TITLE = "UI.Game Prototype";
    private Thread thread;
    private boolean running;

    //Size
    public static final int WIDTH = 270;
    public static final int HEIGHT = WIDTH / 14*10;
    public static final int SCALE = 4;
    public static final int TILE_SIZE = 64;
    public static final int playerWidth = 100;
    public static final int playerHeight = 100;

    //Sprite sheet
    private SpriteSheet spriteSheet;

    //UI.Game object
    private static Sprite[] playerMoveFrame;
    public static  Sprite wall;

    //level
    private BufferedImage level;

    //Handler
    public static  Handler handler;

    // Camera
    private Camera cam;

    // KeyListener
    private Input keyListener;

    public Game() {
        running = false;
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    public void paint() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());
        g.translate(cam.getX(), cam.getY());
        handler.paint(g);
        g.dispose();
        bs.show();
    }

    public void update() {
        handler.update();
        for(int i=0;i<handler.getEntities().size();i++) {
            if(handler.getEntities().get(i).getId() == Id.player) {
                cam.update(handler.getEntities().get(i));
            }
        }
    }

    public void init() {
        //Sprite object
        spriteSheet = new SpriteSheet("/res/spriteSheet.png");
        wall = new Sprite(spriteSheet, 1, 1);

        //BufferedImage object
        level = ResourceManager.getInstance().getImage("/res/level.png");

        //Game object
        playerMoveFrame = new Sprite[8];
        handler = new Handler();
        cam = new Camera();

        //Create level
        for(int i=0;i<playerMoveFrame.length;i++) {
            playerMoveFrame[i] = new Sprite(spriteSheet,i + 1, 16);
        }
        handler.createLevel(level);

        keyListener = new Input();

        //KeyInput listener
        addKeyListener(keyListener);
    }

    private synchronized void start() {
        if(running)return;
        running = true;
        thread = new Thread(this, "Thread");
        thread.start();
    }

    private synchronized void stop() {
        if(!running)return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60;
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0 / amountOfTicks;
        int frames = 0;
        int ticks = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                update();
                ticks++;
                delta--;
            }
            paint();
            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.println(frames + " Frame Per Second " + ticks + " Updates Per Second");
                frames = 0;
                ticks = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame(TITLE);
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }

    //getters and setters
    public static Sprite[] getPlayerMoveFrame() {
        return playerMoveFrame;
    }
}
