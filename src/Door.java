import java.awt.Color;
import java.awt.Rectangle;

public class Door {

	Color c;
	int pos;
	boolean exit = false;
	
	public Door(Color c, int pos) {
		this.c = c;
		this.pos = pos;
	}
	
	public Rectangle getCollisionRect(int camera, int height) {
		return new Rectangle(pos - camera + 96, height - 120, 96, 96);
	}
}
