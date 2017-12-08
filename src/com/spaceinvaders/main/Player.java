package com.spaceinvaders.main;

import java.awt.*;
import java.util.Random;

/**
 * Created by justinjoco on 12/7/17.
 */
public class Player extends GameObject{

    Random r = new Random();
    Handler handler;
    int bulletVel = 3;
    static int startX, startY;

    public Player(int x, int y, ID id, Handler handler){
        super(x, y, id);
        this.handler = handler;
       // velX = r.nextInt(5) + 1;
      //  velY = r.nextInt(5);
        startX = x;
        startY = y;

    }

    public void tick() {
        x+=velX;
        y+=velY;

        x = Game.clamp(x, 0, Game.WIDTH -32);
        y = Game.clamp(y, 0 ,Game.HEIGHT - 16);


        collision();

    }



    private void collision(){
        for (int i = 0; i< handler.object.size();i++){
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Bullet ){
                if (getBounds().intersects(temp.getBounds())){
                    HUD.HEALTH-=1;
                    if (HUD.HEALTH<=0) {
                        handler.removeObject(temp);
                        handler.removeObject(this);
                    }else{
                        handler.removeObject(temp);
                        handler.addObject(new Player(startX, startY, ID.Player, handler));
                        handler.removeObject(this);

                    }



                }

            }

            if (temp.getId() == ID.Enemy){
                if (getBounds().intersects(temp.getBounds())){
                    HUD.HEALTH-=1;
                    handler.removeObject(temp);
                    handler.addObject(new Player(startX, startY, ID.Player, handler));
                    handler.removeObject(this);
                }

            }

        }

    }


    public void render(Graphics g) {


        g.setColor(Color.orange);
        g.fillRect(x, y, 64, 32);

    }


    public Rectangle getBounds() {
        return new Rectangle(x, y, 64, 32);
    }


}
