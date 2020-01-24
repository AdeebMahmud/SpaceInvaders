
import java.awt.*;
/**
 *
 * @author Adeeb Mahmud
 */
public class Civilian {
    
    //FIELDS
    
    int x;
    int y;
    int speed;
    int r;
    int health;
    Color color1;
    boolean moveRight;
    boolean moveLeft;
    boolean isAlive; //will be turned false once hit by projectile
    
    //CONSTRUCTOR
    
    public Civilian() {
        
        health = 1;
        moveRight = true;
        moveLeft = false;
        isAlive = true;
        
        x = (int)((Math.random() * GamePanel.WIDTH-100) + 1); // random x-coordinate within board dimensions (factoring in some padding)
        y = (int)(Math.random() * -40) + GamePanel.HEIGHT-10; // random y-coordinate within board dimensions (factoring in some padding)
        speed = 4;
        r = 7; //radius
        color1 = Color.BLUE;
        
    }
    
    //Getters. Used for collision calculations with other class getters.
    
    public double getX() { return x; };
    public double getY() { return y; };
    public double getR() { return r; };
    
    
    // This function is called when a collision occurs. The health goes to 0 and the civilian is deemed dead and out of play
    public void hit() {
        
        health--;
        if(health <= 0) {
            isAlive = false;
            
        }
        
        //If a civilian gets hit you are penalized
        GamePanel.score -= 100;
    }
    
    // This method is used to give the civilian a random change to switch its direction. It is used in combination with the timer
    // that is located within the Game Loop
    public void directionChange(){
        
        int rand = (int)(Math.random()*6) + 1;
        
        if(rand == 1) {
            
            // Switch direction
            moveRight = !moveRight;
            moveLeft = !moveLeft;
            
        }

    }
    
    //FUNCTIONS 
    public void update() {
        
        //Civilians will be walking in random left and right movement in attempt to flee alien buillets
        
        //Random movement;
        //Direction change is called every 3 seconds in the Game Loop in GamePanel
        if(moveLeft) {
            x -= speed;
        }
        
        if(moveRight) {
            x += speed;
        }
        
        //OUT OF BOUNDS CONTROL
        if (x < r ) x = r;
        if ( y < r) y = r;
        if (x > GamePanel.WIDTH -r ) x = GamePanel.WIDTH -r;
        if ( y > GamePanel.HEIGHT -r ) y = GamePanel.HEIGHT -r;
        
    }
    
    // Draw as oval utilizing x and y coordinates as dimensions
    public void draw(Graphics2D g) {
        
        g.setColor(color1);
        g.fillOval(x-r, y-r, r, r);
        
    }
    
}