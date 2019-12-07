
import java.awt.Graphics;

// abstract class used by all objects in the game

public abstract class GameObj {
	
	// fields for position, size, velocity, and maximum coordinates without leaving the boundaries
	private int px;
	private int py;
	
	private int height;
	private int width;
	
	private int vx;
	private int vy;
	
    public GameObj(int vx, int vy, int px, int py, int width, int height) {
            this.vx = vx;
            this.vy = vy;
            this.px = px;
            this.py = py;
            this.width  = width;
            this.height = height;
    }
    
    /*** GETTERS **********************************************************************************/
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }
    
    public int getVx() {
        return this.vx;
    }
    
    public int getVy() {
        return this.vy;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    /*** SETTERS **********************************************************************************/

    public void setPx(int px) {
        this.px = px;
        clip();
    }

    public void setPy(int py) {
        this.py = py;
        clip();
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
    
    
    protected void clip() {
        this.px = Math.min(Math.max(this.px, 0), GameCourt.COURT_WIDTH);
        this.py = Math.min(Math.max(this.py, 0), GameCourt.COURT_HEIGHT);
    }
    
    public void move() {
        this.px += this.vx;
        this.py += this.vy;

        clip();
    }
    
    public void moveX() {
        this.px += this.vx;

        clip();
    }
    
    public void moveY() {
        this.py += this.vy;

        clip();
    }
    
    public boolean intersects(GameObj that) {
        return (this.px + this.width >= that.px
            && this.py + this.height >= that.py
            && that.px + that.width >= this.px
            && that.py + that.height >= this.py);
    }
    
    public boolean willIntersect(GameObj that) {
        int thisNextX = this.px + this.vx;
        int thisNextY = this.py + this.vy;
        int thatNextX = that.px + that.vx;
        int thatNextY = that.py + that.vy;
        return (thisNextX + this.width > thatNextX
                && thisNextY + this.height > thatNextY
                && thatNextX + that.width > thisNextX 
                && thatNextY + that.height > thisNextY);
        }
        
    public boolean willIntersectX(GameObj that) {
        int thisNextX = this.px + this.vx;
        int thatNextX = that.px + that.vx;
        return (thisNextX + this.width > thatNextX
                && thatNextX + that.width > thisNextX &&
                this.py <= that.py + that.height &&
                this.py + this.height >= that.py);
        }
    
    public boolean willIntersectY(GameObj that) {
        int thisNextY = this.py + this.vy;
        int thatNextY = that.py + that.vy;
        return (thisNextY + this.height > thatNextY
                && thatNextY + that.height > thisNextY &&
                this.px <= that.px + that.width &&
                this.px + this.width >= that.px);
        }    

    
    public abstract void draw(Graphics g);

}
