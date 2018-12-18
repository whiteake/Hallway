import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Sound {

	public static void playSound(String filename) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					new Player(new BufferedInputStream(new FileInputStream(new File(filename)))).play();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}).start();
	}
}
