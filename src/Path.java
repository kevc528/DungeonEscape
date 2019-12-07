import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// class for the paths in the maze
public class Path extends GameObj {
	
	private static final int SIZE = 30;
	
	private static final String FILE_NAME = System.getProperty("user.dir") + "/files/path.png";
	
    private static BufferedImage img;
	
	public Path(int posX, int posY) {
		super(0, 0, posX, posY, SIZE, SIZE);
		
        try {
            if (img == null) {
                img = ImageIO.read(new File(FILE_NAME));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        } 
	}
	
	public static int getSize() {
		return SIZE;
	}
	
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}
