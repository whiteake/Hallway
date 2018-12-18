import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Animation {

	private Timer t;
	private Image currentFrame;
	private int index = 0;
	
	public Animation(Image[] frames, int time) {
		this.currentFrame = frames[0];
		this.t = new Timer(time, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(index);
				currentFrame = frames[index];
				if (index == frames.length - 1) {
					index = 0;
				} else {
					index++;
				}
			}
			
		});
	}
	
	public void startAnimation() {
		this.t.start();
	}
	
	public void stopAnimation() {
		this.t.stop();
	}
	
	public Image getCurrentFrame() {
		return currentFrame;
	}
}
