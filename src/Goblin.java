// This class is for the Goblin monster
public class Goblin extends Monster{
	
	private static final String imgFile = System.getProperty("user.dir") + "/files/goblin.png";
	
	public Goblin(int posX, int posY) {
		super(imgFile, posX, posY);
	}
	
	// the attack method does -1 for goblin
	public void attack(Player p) {
		p.decrHealth(1);
	}

}
