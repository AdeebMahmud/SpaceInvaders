
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartScreen extends JPanel {
    
    JTextField usernameInput;
    JPanel endScreen;
    
    public StartScreen(){
        usernameInput = new JTextField();
        endScreen = new JPanel();
       
    }
    
    public void draw(Graphics2D g) {
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        g.drawString("SPACE INVADERS", GamePanel.WIDTH/7 + 30, GamePanel.HEIGHT/2 - 100);
        g.drawString("PRESS ENTER TO PLAY", GamePanel.WIDTH/7, GamePanel.HEIGHT/2);
        
        add(usernameInput);
        add(endScreen);
    }
}