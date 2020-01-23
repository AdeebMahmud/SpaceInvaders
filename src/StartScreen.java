
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class StartScreen {
    
    public void draw(Graphics2D g) {
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        g.drawString("PRESS ENTER TO PLAY", GamePanel.WIDTH/7, GamePanel.HEIGHT/2);
    }
}