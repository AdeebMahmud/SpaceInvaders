
import java.awt.*;
import java.lang.Math;
import javax.swing.ImageIcon;
/**
 *
 * @author Adeeb Mahmud
 */
public class Civilian  {
    
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
    
    Image image;
    
    //CONSTRUCTOR
    
    public Civilian() {
        
        health = 1;
        moveRight = true;
        moveLeft = false;
        isAlive = true;
        
        x = (int)((Math.random() * GamePanel.WIDTH-100) + 1); //random x within board dimensions
        y = (int)(Math.random() * -40) + GamePanel.HEIGHT-10;
        speed = 4;
        r = 7; //radius
        color1 = Color.BLUE;
        
        ImageIcon civilianSprite = new ImageIcon("src/resources/civilian.png");
        image = civilianSprite.getImage();
    }
    
    public double getX() { return x; };
    public double getY() { return y; };
    public double getR() { return r; };
        
    public void hit() {
        health--;
        if(health <= 0) {
            isAlive = false;
            
        }
        
        //If a civilian gets hit you are penalized
        GamePanel.score -= 100;
    }
    
    public void directionChange(){
        
        int rand = (int)(Math.random()*6) + 1;
        
        if(rand == 1) {
            
            moveRight = !moveRight;
            moveLeft = !moveLeft;
            
        }

    }
    
    //FUNCTIONS 
    public void update() {
        
        
        //Civilians will be walking in random left and right movement in attempt to flee alien buillets
        
        //Random movement;
        //Direction change is called every 3 seconds in the GAME LOOP in GamePanel
        if(moveLeft) {
            x -= speed;
        }
        
        if(moveRight) {
            x += speed;
        }
        
        //OUT OF BOUNDS
        if (x < r ) x = r;
        if ( y < r) y = r;
        if (x > GamePanel.WIDTH -r ) x = GamePanel.WIDTH -r;
        if ( y > GamePanel.HEIGHT -r ) y = GamePanel.HEIGHT -r;
        
    }
    
    public void draw(Graphics2D g) {
        
        g.setColor(color1);
        g.fillOval(x-r, y-r, r, r);
        
    }
    
}