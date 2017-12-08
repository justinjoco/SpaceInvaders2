package com.spaceinvaders.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by justinjoco on 12/7/17.
 */
public class KeyInput extends KeyAdapter {

    private Handler handler;
    private AtomicBoolean canShoot = new AtomicBoolean(true);

    public KeyInput(Handler handler){
        this.handler = handler;
    }


    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        for (int i = 0; i< handler.object.size(); i++){
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Player){

                if ((key == KeyEvent.VK_LEFT) || (key == KeyEvent.VK_A)) temp.setVelX(-3);
                if ((key == KeyEvent.VK_RIGHT)|| (key == KeyEvent.VK_D)) temp.setVelX(3);

                if ((key == KeyEvent.VK_SPACE)) {
                  //  System.out.println("FIRE");
                    if (canShoot.compareAndSet(true,false))
                        handler.addObject(new Bullet(temp.getX()+32, temp.getY()-5, ID.Bullet, 5, handler));
                }



            }
        }
       // System.out.println(key);

        if (key == KeyEvent.VK_ESCAPE) System.exit(1);
    }


    public void keyReleased(KeyEvent e){

        int key = e.getKeyCode();

        for (int i = 0; i< handler.object.size(); i++){
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Player){
                //Key events for player 1
                if ((key == KeyEvent.VK_LEFT) || (key == KeyEvent.VK_A)) temp.setVelX(0);
                if ((key == KeyEvent.VK_RIGHT)|| (key == KeyEvent.VK_D)) temp.setVelX(0);
                if ((key == KeyEvent.VK_SPACE)) {
                    //  System.out.println("FIRE");
                    canShoot.set(true);
                }


            }
        }

    }
}
