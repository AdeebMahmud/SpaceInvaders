
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GameOverScreen {
    
    public void draw(Graphics2D g) {
        
        Font font_0 = new Font("arial", Font.BOLD, 30);
        g.setFont(font_0);
        g.setColor(Color.WHITE);
        g.drawString("GAME OVER!", GamePanel.WIDTH/2-100, GamePanel.HEIGHT/2-100);
        
        Font font_1 = new Font("arial", Font.BOLD, 20);
        g.setFont(font_1);
        g.drawString("SCORE: " + GamePanel.score, GamePanel.WIDTH/2-70, GamePanel.HEIGHT/2-50);
        g.drawString("HIT ENTER TO PLAY AGAIN", GamePanel.WIDTH/2-150, GamePanel.HEIGHT/2);
        //JText Field
    }
    
} 
