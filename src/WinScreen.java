
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
//SCORES FROM XML DATA
public class WinScreen {
    
    public void draw(Graphics2D g) {
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        g.drawString("YOU WIN!", GamePanel.WIDTH/2-100, GamePanel.HEIGHT/2-100);
        Font fnt1 = new Font("arial", Font.BOLD, 10);
        g.drawString("HIGH SCORES", GamePanel.WIDTH/2-130, GamePanel.HEIGHT/2+50);
        
        /*if(newHigh) {
            g.drawString("NEW HIGH SCORE!", GamePanel.WIDTH/2-130, GamePanel.HEIGHT/2);
        }*/
        //Ask for a name submission input field
        //XML DATA FORMATTING
    }
}
