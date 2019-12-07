
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// class for player, what the user will control during the game
public class Player extends GameObj{
	
	// attributes about the player
    private static final String IMG_FILE = System.getProperty("user.dir") + "/files/player.png";
    private static final int SIZE = 20;
    private static final int INIT_POS_X = 31;
    private static final int INIT_POS_Y = 31;
    private static final int INIT_VEL_X = 0;
    private static final int INIT_VEL_Y = 0;
    private int health = 5;
	
    private static BufferedImage img;
    
    public Player () {
    	super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE);
    	
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        	System.out.println(IMG_FILE);
        }
    }
    
    public int getHealth() {
    	return health;
    }
    
    // decreasing the health, used for when monsters attack
    public void decrHealth(int d) {
    	if (health - d >= 0) {
        	this.health -= d;
    	} 
    	else {
    		this.health = 0;
    	}
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
    
}
