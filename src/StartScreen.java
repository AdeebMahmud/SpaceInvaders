
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

// This screen is seen once at the beginning of every running of the program
public class StartScreen {
    
    public StartScreen(){
        
    }
    
    public void draw(Graphics2D g) {
        
        // Use Graphics2d to draw a simple start screen text display
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        g.drawString("SPACE INVADERS", GamePanel.WIDTH/7 + 30, GamePanel.HEIGHT/2 - 100);
        g.drawString("PRESS ENTER TO PLAY", GamePanel.WIDTH/7, GamePanel.HEIGHT/2);
        
    }
}