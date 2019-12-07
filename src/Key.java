import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// this class is for the Key object in the maze
public class Key extends GameObj {
    private static final String IMG_FILE = System.getProperty("user.dir") + "/files/key.png";
    private static final int SIZE = 30;

    private static BufferedImage img;

    public Key(int posX, int posY) {
        super(0, 0, posX, posY, SIZE, SIZE);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        	System.out.println(IMG_FILE);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}
