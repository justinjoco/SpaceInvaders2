package com.spaceinvaders.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
/**
 * Created by justinjoco on 12/3/17.
 */
public class Game extends Canvas implements Runnable{


    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
    public static int rows, columns, velX, velY, fireRate;
    private Thread thread;
    private boolean running = false;
    private static Handler handler;
    private static Random r;
    private HUD hud;
    public static Enemy [][] enemies;

    public Game() {

        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));


        Sound explosion = new Sound ("./src/com/spaceinvaders/main/explosion.wav");

        Bullet.effect = explosion;
        new Window(WIDTH, HEIGHT, "Space Invaders", this);

        hud = new HUD();

        r = new Random();
        int i, j;

        //Set up player
        handler.addObject(new Player(WIDTH / 2 - 16, HEIGHT * 9 / 10, ID.Player, handler));


        enemies = new Enemy[rows][columns];
        //Set up enemies

        for (i = 0; i < columns; i++) {
            for (j = 0; j < rows; j++) {
              //  handler.addObject(new Enemy(0 + 40 * i, HEIGHT / 12 + 20 * j, ID.Enemy, i,j,velX, velY, handler));
                Enemy newEnemy = new Enemy(0 + 40 * i, HEIGHT / 12 + 20 * j, ID.Enemy, j,i,velX, velY, handler, enemies);
                handler.addEnemy(newEnemy);
                enemies[j][i] = newEnemy;
                if (j == rows -1) enemies[j][i].invaderBelow = false;
            }
        }


        //Set up Barrier1
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 5; j++) {
                handler.addObject(new Barrier(WIDTH / 4 - 16 + i * 8, HEIGHT * 7 / 10 + j * 8, ID.Barrier, handler));
            }
        }


        //Set up Barrier2
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 5; j++) {
                handler.addObject(new Barrier(WIDTH * 3 / 4 - 16 + i * 8, HEIGHT * 7 / 10 + j * 8, ID.Barrier, handler));
            }
        }


    }


    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }


    public synchronized void stop() {
        try {
            thread.join();
            running = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void run() {

        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //    System.out.println("FPS: "+ frames);
                frames = 0;
            }


        }
        stop();

    }


    private void tick() {

        handler.tick();
        hud.tick();
    }

    private void render() {

        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }


        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);

        hud.render(g);

        g.dispose();
        bs.show();

    }

    public static int clamp(int var, int min, int max) {
        if (var >= max)
            return var = max;
        else if (var <= min)
            return var = min;
        else
            return var;


    }

    public static void main(String[] args) throws InterruptedException {


        Scanner sc = new Scanner(System.in);

        System.out.println("Hello, user! Welcome to Space Invaders");

        System.out.println("Number of invader rows?");
        rows = sc.nextInt();

        System.out.println("Columns?");
        columns = sc.nextInt();

        HUD.numEnemies = rows * columns;


        System.out.println("Invader horizontal velocity?");
        velX = sc.nextInt();

        System.out.println("Vertical?");
        velY = sc.nextInt()*4;

        System.out.println("Invader fire rate?");
        fireRate = sc.nextInt();

        System.out.println("Number of lives?");
        HUD.HEALTH = sc.nextInt();


        new Game();

        Thread t1 = new Thread(new Sound("./src/com/spaceinvaders/main/ChillingMusic.wav"));
        t1.start();
        Random r = new Random();

        int countdown =0;
        int tempRows = rows;
        int maxSize = handler.enemies.size();
        while (true) {
            //  System.out.println(countdown);
            //    ++countdown;
            Enemy enemy;
            try {
                enemy = handler.enemies.get(r.nextInt(handler.enemies.size()));
            }catch(Exception e){
                break;
            }



            while (enemies[enemy.rowIndex][enemy.colIndex].invaderBelow) {

                enemy = handler.enemies.get(r.nextInt(handler.enemies.size()));

            }


          //  System.out.println("___________");


            handler.addObject(new Bullet(enemy.getX() + 16, enemy.getY() + 16, ID.Bullet, -5, handler));
            countdown = 0;
            /*
            if (temp.getId() == ID.Enemy) {

                handler.addObject(new Bullet(temp.getX() + 16, temp.getY() + 16, ID.Bullet, -5, handler));
            } else {
                while (temp.getId() != ID.Enemy) {

                    temp = handler.object.get(r.nextInt(handler.object.size() - 1));
                    if ((temp.getId() == ID.Enemy) && (temp.colIndex == columns-1)) {

                        handler.addObject(new Bullet(temp.getX() + 16, temp.getY() + 16, ID.Bullet, -5, handler));
                        break;

                    }
                }
*/

                Thread.sleep(2000 / fireRate);
            }


        }
    }
