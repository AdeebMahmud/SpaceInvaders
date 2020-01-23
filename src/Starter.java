/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adeeb Mahmud
 */
import javax.swing.*;

public class Starter {
 
    public static void main(String[] args) {
        
        JFrame window = new JFrame("Space Invaders");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setContentPane(new GamePanel());
        window.pack();
        window.setVisible(true);
        
    }
    
}
