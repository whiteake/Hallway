import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Forewarner {

	int pos;
	private Random r = new Random();
	boolean hint = false;

	public Forewarner(int pos) {
		this.pos = pos;
	}

	public String getStatement(ArrayList<Door> doors) {
		if (hint) {
			for (Door d: doors) {
				if (d.exit) {
					return getRandomString() + d.c + "DOOR " + d.c + " DOOR" + d.c + "DOOR RUN RUN RUN RUN RUN RUN RUN RUN";
				}
			}
		} else {
			return getRandomString();
		}
		return null;
	}

	public String getRandomString() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String punctuation = ".!?;:";
		String sentence = "";
		for (int j = 0; j < r.nextInt(20) + 1; j++) {
			String word = "";
			for (int i = 0; i < r.nextInt(6) + 1; i++) {
				word += characters.toCharArray()[r.nextInt(characters.length())];
			}
			sentence += word + " ";
		}
		return sentence += punctuation.toCharArray()[r.nextInt(punctuation.length())];
	}

	public Rectangle getCollisionRectangle(int camera, int height) {
		return new Rectangle(pos - camera - 50, height - 102, 74, 96);
	}
}
