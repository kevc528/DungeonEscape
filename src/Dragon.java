// This class is for the Dragon monsters
public class Dragon extends Monster{
	
	private static final String imgFile = System.getProperty("user.dir") + "/files/dragon.png";
	
	public Dragon(int posX, int posY) {
		super(imgFile, posX, posY);
	}
	
	// Dragons attack method does -2
	public void attack(Player p) {
		p.decrHealth(2);
	}
}
