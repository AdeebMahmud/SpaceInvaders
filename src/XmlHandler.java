
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;


/**
 *
 * @author Adeeb
 */

// This class is used to handle Xml writing.
public class XmlHandler {
    
    String filepath;
    Document document;
    DocumentBuilder docBuilder;
    DocumentBuilderFactory docFactory;
    
    
    public XmlHandler() {
        
    }
    
    // Call write score() on JText Field event listener at WinScreen. This function is used to write the player scores to an xml file.
    // This allows scores to be saved in between program instances.
    // Take in the username and the score and save it to scores.xml
    public void writeScore(String username, String roundScore) {
        
        try {
            
            // Xml Reading
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            filepath = "src/scores.xml";
            document = docBuilder.parse(filepath);
            document.getDocumentElement().normalize();
            
            //Get the root node scores
            Node scores = document.getFirstChild();
            
            //Create new score element - child of scores
            Element score = document.createElement("score");
            
            // Give each score element a name representing the player who submitted the score and the score
            score.setAttribute("name", username);
            
            score.setAttribute("value", roundScore);
            
            scores.appendChild(score); //Appends score to scores
            
            // Writes to the external scores.xml file
            document.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
             
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
           } catch (TransformerException ex) {
            Logger.getLogger(XmlHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
