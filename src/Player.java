
import java.awt.*;

/**
 *
 * @author adeeb
 */
//The Player is the controllable allied unit which the real-world player has control of.
public class Player {
    
    //FIELDS
    int x;
    int y;
    int r;
    
    int dx;
    int dy;
    int speed;
    int lives;
    
    boolean moveRight;
    boolean moveLeft;
    boolean isAlive;
    boolean firing;
    
    // Used to control fire rate
    long firingTimer;
    long firingDelay;
    
    Color color1;
    
    
    //CONSTRUCTOR
    public Player() {
        
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT-60;
        r = 5;
        
        lives = 3; //code lives, on hit colour change
        isAlive = true;
        
        moveRight = false;
        moveLeft = false;
        
        firing = false;
        firingTimer = System.nanoTime();
        firingDelay = 400; //How fast the player can shoot. Avoids rapid fire to control game difficulty.
        
        dx = 0;
        dy = 0;
        speed = 5;
        color1 = Color.GREEN;
        
    }
    
    //FUNCTIONS
    
    // Used in GamePanel event listeners
    public void setMoveLeft(boolean b) {moveLeft = b;}
    public void setMoveRight(boolean b) {moveRight = b;}
    public double getX() { return x; };
    public double getY() { return y; };
    public double getR() { return r; };
    
    // The player starts with 3 lives. The live counter goes down and a visual indicator is given to the player every time it is hit.
    public void hit() {
        
        lives--;
        
        //Visual indicator of live loss
        switch(lives) {
            case 2:
                color1 = Color.YELLOW;
                break;
            case 1:
                color1 = Color.RED;
            default:
                break;
        }
       
        //Check if the player is alive. Player is not alive once lives = 0;
        if(lives <= 0) {
            isAlive = false;
            
        }
        
        //If you are hit you are penalized
        GamePanel.score -= 100;
    }
    
    
    public void update() {
        
        if(moveLeft) {
            dx = -speed;
        }
        
        if(moveRight){
            dx = speed;
        }
        
        // Controls the player fire
        // A fire rate is implemented to add difficulty to the game
        
        if(firing) {
            long elapsed = (System.nanoTime() - firingTimer)/ 1000000;
            
            if(elapsed > firingDelay) {
                GamePanel.bullets.add(new Bullet(270, x, y)); // Add a new bullet to the bulletsArray in GamePanel
                firingTimer = System.nanoTime();
            }
        }
        
        // Update x and y according to player movement and speed
        x += dx; 
        y += dy;
        
        //Out of bounds control
        if (x < r ) x = r;
        if ( y < r) y = r;
        if (x > GamePanel.WIDTH -r ) x = GamePanel.WIDTH -r;
        if ( y > GamePanel.HEIGHT -r ) y = GamePanel.HEIGHT -r;
        
        dx = 0; //Prevents the player unit from moving in one direction infinately after one key press.
        dy = 0;
        
    }
    
    // Draw as oval utilizing x and y coordinates as dimensions
    public void draw(Graphics2D g) {
        
        g.setColor(color1);
        g.fillOval(x-r, y-r, 4*r, 2*r);
                
    }
    
    public void setFiring(boolean b) {
        firing = b;
    }
}