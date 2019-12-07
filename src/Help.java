import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

// this class is for the help menu for the help mode
public class Help {
	
    private static final String IMG_FILE = System.getProperty("user.dir") + "/files/title.png";
    private static BufferedImage img;

	
	public Help() {
		try {
	        if (img == null) {
	            img = ImageIO.read(new File(IMG_FILE));
	        }
	    } catch (IOException e) {
	        System.out.println("Internal Error:" + e.getMessage());
	    	System.out.println(IMG_FILE);
	    }
	}
	
	// helper function for centering a block of text
	void drawString(Graphics g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	
	public void draw(Graphics g) {
		
		String directions = 
				"Welcome to Dungeon Escape. In this game,\n"
				+ "you will be placed in a dark maze.\n"
				+ "To escape this maze, you have to find\n"
				+ "all of the keys, but beware of the \n"
				+ "monsters that spawn after 5 seconds.\n"
				+ "The dragons are strong and damage you\n"
				+ "more than the goblins. If you get hit\n"
				+ "you will have two seconds of invicibility\n"
				+ "Find all the keys in the maze while\n"
				+ "escaping the monsters by moving using\n"
				+ "WASD or the arrow keys. High scores are\n"
				+ "determined by how quickly you complete\n"
				+ "the maze.";
		
        g.drawImage(img, 0, 0, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT, null);
        
        g.setColor(Color.WHITE);
    	Font f = new Font("Courier New", Font.BOLD, 25);
        g.setFont(f);
        
        drawString(g, directions, 40, 150);

	}
}
