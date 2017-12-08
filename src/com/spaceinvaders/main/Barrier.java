package com.spaceinvaders.main;

import java.awt.*;

/**
 * Created by justinjoco on 12/7/17.
 */
public class Barrier extends GameObject {


    private Handler handler;
    public Barrier(int x, int y, ID id, Handler handler){
        super(x, y, id);
        this.handler = handler;
    }

    public void tick() {

        collision();

    }


    public void render(Graphics g) {

        g.setColor(Color.gray);
        g.fillRect(x, y, 8, 8);


    }

    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Bullet) {
                if (getBounds().intersects(temp.getBounds())) {
                    handler.removeObject(temp);

                    handler.removeObject(this);
                }

            }

        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }
}
