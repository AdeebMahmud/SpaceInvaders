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

public class Bullet {
    
    // FIELDS
    
    double x; // x-coordinate
    double y; // y-coordinate
    int r; // radius for hitbox 
    
    double dx; // updating x value
    double dy; // updating y value
    double rad; //radians angle
    double speed; // bullet speed
    
    Color bulletColor; // bullet color
    
    //CONSTRUCTOR
    public Bullet(double angle, int x, int y) {
        
        this.x = x;
        this.y = y;
        r = 3;
        
        rad = Math.toRadians(angle);
        
        speed = 10;
        
        dx = Math.cos(rad) * speed; //
        dy = Math.sin(rad) * speed;
        
        bulletColor = Color.GREEN;
    }
    
    //FUNCTIONS
    
    // Getters. Used during collision checks with other class entity getters
    public double getX() { return x; };
    public double getY() { return y; };
    public double getR() { return r; };
    
    public boolean update() {
        
        // Constantly changing x value by its speed
        x += dx;
        y += dy;
        
        // Checks if the bullet is still in play
        if (x < -r || x > GamePanel.WIDTH + r || y < -r || y > GamePanel.HEIGHT + r) {
            return true;
        }
        
        return false;
        
    }
    
    // Draw bullets as ovals
    public void draw(Graphics g) {
        
        g.setColor(bulletColor);
        g.fillOval((int)(x - r), (int) (y-r), 2*r, 2*r);
        
    }
}
