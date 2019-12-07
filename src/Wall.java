import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// this class is for the walls in the maze
public class Wall extends GameObj {
	
	private static final int SIZE = Path.getSize();
	
	private static final String FILE_NAME = System.getProperty("user.dir") + "/files/wall.png";
	
    private static BufferedImage img;
	
	public Wall(int posX, int posY) {
		super(0, 0, posX, posY, SIZE, SIZE);
		
        try {
            if (img == null) {
                img = ImageIO.read(new File(FILE_NAME));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        	System.out.println(FILE_NAME);
        } 
	}
	
	
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}
