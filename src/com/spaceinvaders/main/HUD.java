package com.spaceinvaders.main;

import java.awt.*;

/**
 * Created by justinjoco on 12/7/17.
 */
public class HUD {

    public static int HEALTH;
    public static int numEnemies;
    public static long score = 0;

    public void tick(){

        HEALTH = Game.clamp(HEALTH, 0, 100);

        numEnemies = Game.clamp(numEnemies, 0, 1000);


    }

    public void render(Graphics g){
        g.setColor(Color.white);
        g.drawString("HEALTH : " + HEALTH, 15, 15);
        g.drawString("SCORE : " + score, 100, 15);
        g.drawString("ENEMIES : " + numEnemies, 200, 15);

        if (HEALTH <=0){
            g.drawString("YOU HAVE LOST ALL YOUR LIVES", Game.WIDTH/3, Game.HEIGHT/2-20);
        }

        if (numEnemies <=0){
            g.drawString("ALL ENEMIES HAVE BEEN DEFEATED", Game.WIDTH/3, Game.HEIGHT/2-20);
        }

    }
}
