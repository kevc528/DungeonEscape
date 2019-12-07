
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// this menu class is used for the start screen of the game

public class Menu {
	
    private static final String IMG_FILE = System.getProperty("user.dir") + "/files/title.png";
    private static final String BUTTON_FILE = System.getProperty("user.dir") + "/files/button.png";
    private static BufferedImage img;
    private static BufferedImage imgButton;
    
    public Menu() {
	    try {
	        if (img == null) {
	            img = ImageIO.read(new File(IMG_FILE));
	        }
	    } catch (IOException e) {
	        System.out.println("Internal Error:" + e.getMessage());
	    	System.out.println(IMG_FILE);
	    }
	    
	    try {
	        if (imgButton == null) {
	            imgButton = ImageIO.read(new File(BUTTON_FILE));
	        }
	    } catch (IOException b) {
	        System.out.println("Internal Error:" + b.getMessage());
	    	System.out.println(BUTTON_FILE);
	    }
    }
    
    public void draw(Graphics g) {
        
    	int size = 40;
    	
    	Font f = new Font("Courier New", Font.BOLD, size);
    	
        FontMetrics metrics = g.getFontMetrics(f);

        g.setFont(f);
        g.setColor(Color.WHITE);
        
        g.drawImage(img, 0, 0, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT, null);
        
        g.drawString("Dungeon Escape", GameCourt.COURT_WIDTH/2 - 
        		metrics.stringWidth("Dungeon Escape")/2, 100);
        
        // drawing the buttons that will be play, scores, and help
        g.drawImage(imgButton, GameCourt.COURT_WIDTH/2-100, 200, 200, 50, null);
        g.drawImage(imgButton, GameCourt.COURT_WIDTH/2-100, 350, 200, 50, null);
        g.drawImage(imgButton, GameCourt.COURT_WIDTH/2-100, 500, 200, 50, null);

        
        g.setColor(Color.BLACK);
        
        g.drawString("Play", GameCourt.COURT_WIDTH/2 - metrics.stringWidth("Play")/2, 
        		200 + metrics.getHeight() - metrics.getHeight()/4);
        g.drawString("Scores", GameCourt.COURT_WIDTH/2 - metrics.stringWidth("Scores")/2, 
        		350 + metrics.getHeight() - metrics.getHeight()/4);
        g.drawString("Help", GameCourt.COURT_WIDTH/2 - metrics.stringWidth("Help")/2, 
        		500 + metrics.getHeight() - metrics.getHeight()/4);
    }
}
