package com.spaceinvaders.main;

import java.awt.*;

/**
 * Created by justinjoco on 12/7/17.
 */
public class Enemy extends GameObject {

    private Handler handler;
    public boolean invaderBelow;
    public Enemy [][] enemies;
    public Enemy(int x, int y, ID id, int rowIndex, int colIndex,int velX, int velY, Handler handler, Enemy [][] enemies){
        super(x, y, id);

        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.velX = velX;
        this.velY = velY;
        this.handler = handler;
        this.invaderBelow = true;
        this.enemies = enemies;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 16);
    }



    public void tick() {
        x+=velX;
       // y+=velY;

       // if (y <=0 || y>= Game.HEIGHT - 32) velY*=-1;
        if (x <=0 || x>= Game.WIDTH - 32) {
           // enemyVelX *= -1;
            for (int i = 0; i< handler.object.size();i++){
                GameObject temp = handler.object.get(i);
                if (temp.getId() == ID.Enemy){
                    temp.y += velY;
                    temp.velX *=-1;
                }

            }




        }

        if (y <=0 || y>= Game.HEIGHT - 32) velY=0;

        collision();
    }

    public boolean invadersAbove(int rowIndex, int colIndex){

        for (int i = 0; i<rowIndex ; i++){
            if (enemies[i][colIndex]!=null)
                return true;

        }

        return false;
    }

    public int getNextRowIndex(int rowIndex, int colIndex){

        for (int i = rowIndex-1; i>=0; i--){
            if (enemies[i][colIndex]!=null)
                return i;

        }

        return -1;
    }



    private void collision(){
        for (int i = 0; i< handler.object.size();i++){
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Bullet ){
                if (getBounds().intersects(temp.getBounds())){
                    handler.removeObject(temp);
                    HUD.score += 1;
                    HUD.numEnemies-=1;
                    if (rowIndex>0 && invadersAbove(rowIndex, colIndex))
                        if (!this.invaderBelow) {
                            int nextRowIndex = getNextRowIndex(rowIndex, colIndex);
                            enemies[nextRowIndex][colIndex].invaderBelow = false;
                        }

                    enemies[rowIndex][colIndex] = null;
                    handler.removeEnemy(this);
                }

            }




        }

    }



    public void render(Graphics g) {


        g.setColor(Color.green);
        g.fillRect(x, y, 32, 16);
    }


}
