import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// abstract class for monsters
public abstract class Monster extends GameObj {
	
    private static final int SIZE = 20;
    private static final int INIT_VEL_X = 2;
    private static final int INIT_VEL_Y = 2;
	
    private BufferedImage img;
    
    // constructor takes in image file and draw will draw image
    public Monster (String file, int posX, int posY) {
    	super(INIT_VEL_X, INIT_VEL_Y, posX, posY, SIZE, SIZE);
    	
        try {
            if (img == null) {
                img = ImageIO.read(new File(file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        	System.out.println(file);
        }
    }
    
    // abstract method for attack, which will be different depending on monsters
    public abstract void attack(Player p);
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }

}
