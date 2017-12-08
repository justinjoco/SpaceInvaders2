package com.spaceinvaders.main;

import java.awt.*;


/**
 * Created by justinjoco on 12/7/17.
 */
public class Bullet extends GameObject{

    private Handler handler;
    public static Sound effect;
    public Bullet(int x, int y, ID id, int speed, Handler handler){
        super(x, y, id);
        this.velY = speed;
        this.handler = handler;
        effect.play();

    }

    public void tick() {
        y-=velY;



        if (y>=Game.HEIGHT-16 || y<=0) {
            handler.removeObject(this);
         }


    }


    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, 4, 4);
    }


    public Rectangle getBounds() {
        return new Rectangle(x, y, 4, 4);
    }


}
