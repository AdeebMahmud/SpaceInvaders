
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
//SCORES FROM XML DATA
public class WinScreen {
    
    public Rectangle submitScore = new Rectangle(GamePanel.WIDTH/2 + 120, 150, 100, 50);
    public Rectangle viewScores = new Rectangle(GamePanel.WIDTH/2 + 120, 150, 100, 50);
    public Rectangle playAgain = new Rectangle(GamePanel.WIDTH/2 + 120, 150, 100, 50);
    
    public void draw(Graphics2D g) {
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        g.drawString("YOU WIN!", GamePanel.WIDTH/2-100, GamePanel.HEIGHT/2-100);
        Font fnt1 = new Font("arial", Font.BOLD, 10);
        g.drawString("HIGH SCORES: ", GamePanel.WIDTH/2, GamePanel.HEIGHT-80);
        //Ask for a name submission input field
        //XML DATA FORMATTING
    }
}
