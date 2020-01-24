
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// The WinScreen is displayed when the player wins the game
public class WinScreen {
    
    //FIELDS
    public String username;
    private String filepath;
    private Document document;
    private DocumentBuilder docBuilder;
    private DocumentBuilderFactory docFactory;
    private Score[] scoreArray;
    
    public void draw(Graphics2D g) {
        
        //Draw simple WinScreen text utilizing the graphics library
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        g.drawString("YOU WIN!", GamePanel.WIDTH/2-100, GamePanel.HEIGHT/2-100);
        g.drawString("HIGH SCORES", GamePanel.WIDTH/2-130, GamePanel.HEIGHT/2);
        
        // READING XML DATA FROM EXTERNAL FILE
        try {
            
            // XML READ SETUP
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            filepath = "src/scores.xml";
            document = docBuilder.parse(filepath);
            document.getDocumentElement().normalize();
            
            // Create a new nodeList to retrieve all score tags and their data from scores in the Xml
            NodeList scores = document.getElementsByTagName("score");
            
            scoreArray = new Score[scores.getLength()]; // Initialize a new array of type Score.
            
            // Iterate through each element in the nodeList and append their data to the scoreArray
            
            for (int i = 0; i < scores.getLength(); i ++) {
                
                Node data = scores.item(i);
                Element elementData = (Element)data;
                Score score = new Score(elementData.getAttribute("name"), Integer.parseInt(elementData.getAttribute("value")));
                
                scoreArray[i] = score;
            }
            
            //SORT ARRAY FOR HIGH SCORE DISPLAY
            // Bubble sort implementation from highest to lowest
            for ( int i = 0; i < scoreArray.length; i ++) {
                
                for(int j = 0; j < scoreArray.length-1; j ++ ) {
                    
                    if(scoreArray[j].score < scoreArray[j+1].score){
                        Score temp = scoreArray[j]; 
                        scoreArray[j] = scoreArray[j+1];
                        scoreArray[j+1] = temp;
                    }
                }
            } 
            
            // DISPLAY TOP 5 SCORES
            if(scoreArray.length < 5) {
                
                for(int i = 0; i < scoreArray.length; i++) {
                
                    // Draw the top 5 scores on the WinScreen
                    g.drawString(scoreArray[i].name + ": " + scoreArray[i].score, GamePanel.WIDTH/2-130, GamePanel.HEIGHT/2+40 + i*35);
                    
                }
                
            }
            
            // If there are less than 5 scores, simply print out all elements in the scoreArray
            else {
            
                for(int i = 0; i < 5; i++) {
                
                    // Draw the scores on the WinScreen
                    g.drawString(scoreArray[i].name + ": " + scoreArray[i].score, GamePanel.WIDTH/2-130, GamePanel.HEIGHT/2+50 + i*35);
                    
                }
            }
        }
        
        catch (UnsupportedEncodingException e) {
            System.out.println(
             "This VM does not support the Latin-1 character set."
            );
          }
          catch (IOException e) {
            System.out.println(e.getMessage());
          }
        catch (ParserConfigurationException | SAXException pce) {
           } 
        }
    }

