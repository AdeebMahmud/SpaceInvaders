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
    
    double x;
    double y;
    int r;
    
    double dx;
    double dy;
    double rad;
    double speed;
    
    Color bulletColor;
    
    //Constructor
    public Bullet(double angle, int x, int y) {
        this.x = x;
        this.y = y;
        r = 3;
        
        rad = Math.toRadians(angle);
        
        speed = 10;
        
        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;
        
        bulletColor = Color.GREEN;
    }
    
    //Functions
    
    public double getX() { return x; };
    public double getY() { return y; };
    public double getR() { return r; };
    
    public boolean update() {
        
        x +=dx;
        y += dy;
        
        if (x < -r || x > GamePanel.WIDTH + r || y < -r || y > GamePanel.HEIGHT + r) {
            return true;
        }
        
        return false;
        
    }
    
    public void draw(Graphics g) {
        
        g.setColor(bulletColor);
        g.fillOval((int)(x - r), (int) (y-r), 2*r, 2*r);
        
    }
}
