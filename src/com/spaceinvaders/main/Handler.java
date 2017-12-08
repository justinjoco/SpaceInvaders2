package com.spaceinvaders.main;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by justinjoco on 12/7/17.
 */
public class Handler {

    LinkedList<GameObject> object = new LinkedList<GameObject>();

    LinkedList<Enemy> enemies = new LinkedList<Enemy>();

    public void tick(){
        for (int i = 0; i < object.size(); i++){
            GameObject temp = object.get(i);
            temp.tick();
        }

    }

    public void render(Graphics g){

        for (int i = 0; i<object.size(); i++){
            GameObject temp = object.get(i);

            temp.render(g);


        }

    }

    public void addObject(GameObject object){
        this.object.add(object);

    }

    public void addEnemy(Enemy enemy){
        this.enemies.add(enemy);
        this.object.add(enemy);

    }

    public void removeObject(GameObject object){
        this.object.remove(object);
    }

    public void removeEnemy(Enemy enemy){

        this.enemies.remove(enemy);
        object.remove(enemy);
    }


}
