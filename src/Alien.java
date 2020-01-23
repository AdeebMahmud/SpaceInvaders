/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adeeb
 */
import java.awt.*;

public class Alien  {
    
    //FIELDS
    int x;
    int y;
    int bulletY;
    int speed;
    int r;
    int health;
    Color color1;
    boolean moveRight;
    boolean moveLeft;
    boolean isAlive; //will be turned false once hit by projectile
    boolean isVisilbe; //will be turned false once isAlive is false
    
    
    boolean firing;
    long firingTimer;
    long firingDelay;
    
    //CONSTRUCTOR
    
    public Alien() {
        
        health = 1;
        moveRight = true;
        moveLeft = false;
        isAlive = true;
        x = 0;
        y = 40;
        speed = 4;
        r = 7; //radius
        color1 = Color.WHITE;
        
        firing = false;
        firingTimer = System.nanoTime();
        firingDelay = 800;
        
    }
    
    //FUNCTIONS
    public void setMoveLeft(boolean b) {moveLeft = b;}
    public void setMoveRight(boolean b) {moveRight = b;}
    
    public double getX() { return x; };
    public double getY() { return y; };
    public double getR() { return r; };
    
    public void hit() {
        
        GamePanel.score += 100;
        health--;
        if(health <= 0) {
            isAlive = false;
        }
    }
    
    public boolean isAlive() { return isAlive; }
    
    public void update() {
        
        if (moveLeft) {
            x -= speed;
        }

        if (moveRight) {
            x += speed;
        }
        
        for(int i = 0; i < GamePanel.aliens.size(); i ++ ) {
            
            if (GamePanel.aliens.get(i).x >= GamePanel.WIDTH) {
            
            //If one alien hits the edge, all change direction to keep them in the same block
                for (Alien n : GamePanel.aliens) {
                    setMoveLeft(true);
                    setMoveRight(false);
                    
                }
                y += 4;
            }

            if (GamePanel.aliens.get(i).x <= 0) {

                //If one alien hits the edge, all change direction to keep them in the same block
                for (Alien n : GamePanel.aliens) {
                    setMoveRight(true);
                    setMoveLeft(false);
                    
                }
                
                y += 4;
            }
            
            if(firing) {
            long elapsed = (System.nanoTime() - firingTimer)/ 1000000;
            
                //shoot a bullet
                if(elapsed > firingDelay) {
                    
                    GamePanel.alienBullets.add(new Bullet(-270, x, y));
                    firingTimer = System.nanoTime();
                    setFiring(false);
                    
                }
            }   
        }
    }
    
    public void draw(Graphics2D g) {
        
        g.setColor(color1);
        g.fillOval((x-r), (y-r), 2*r, 2*r);
        
    }
    
    public void setFiring(boolean b) {
        firing = b;
    }
    
}
