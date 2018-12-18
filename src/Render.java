import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Render extends JComponent {
	private static final long serialVersionUID = 1L;

	ArrayList<Door> doors = new ArrayList<Door>();
	ArrayList<Forewarner> forewarners = new ArrayList<Forewarner>();
	boolean[] keys = new boolean[65535];

	int camera = 30;

	Image checkerboard, background, end;
	Image player1, player2;
	Image forewarner, forewarner2, forewarner3;
	Image door1, door2;

	Rectangle playerRect;

	Animation playerAnimation, forewarnerAnimation;

	boolean gameover = false;

	public Render() {
		try {
			checkerboard = ImageIO.read(new File("checkerboard.png"));
			background = ImageIO.read(new File("background.png"));
			end = ImageIO.read(new File("end.png"));

			player1 = ImageIO.read(new File("char1.png"));
			player2 = ImageIO.read(new File("char2.png"));

			forewarner = ImageIO.read(new File("forewarner.png"));
			forewarner2 = ImageIO.read(new File("forewarner2.png"));
			forewarner3 = ImageIO.read(new File("forewarner3.png"));

			door1 = ImageIO.read(new File("door.png"));
			door2 = ImageIO.read(new File("door (1).png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		playerAnimation = new Animation(new Image[] { player1, player2 }, 500);
		forewarnerAnimation = new Animation(new Image[] { forewarner, forewarner2, forewarner3 }, 500);
		forewarnerAnimation.startAnimation();

		new Timer(16, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}

		}).start();

		addKeyListener(new Keyboard());
		addMouseListener(new Mouse());
		setFocusable(true);

		for (int i = 400; i <= 4000; i += 400) {
			doors.add(new Door(getRandomColor(), i));
		}

		for (int i = 420; i <= 4020; i += 400) {
			forewarners.add(new Forewarner(i));
		}

		doors.get(new Random().nextInt(doors.size())).exit = true;
		// doors.get(0).exit = true;
		forewarners.get(new Random().nextInt(forewarners.size())).hint = true;
	}

	Color getRandomColor() {
		Random r = new Random();
		return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
	}

	public void update() {
		repaint();
		// camera--;
		playerRect = new Rectangle(30, getHeight() - 102, 48, 96);

		if (keys[KeyEvent.VK_W] && !gameover) {
			camera++;

		}

		if (camera > 4500) {
			camera = -1000;
		}

		if (gameover) {
			if (camera < 30) {
				camera++;
			}
			if (camera == 30) {
				JOptionPane.showMessageDialog(null, "Freedom was just another entrance to the hallway.", "Aeternum",
						JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			}
		}

		/*
		 * for (Door d : doors) { if (d.getCollisionRect(camera,
		 * getHeight()).intersects(playerRect)) {
		 * System.out.println("holy shit it worked"); } }
		 */
	}

	public void paint(Graphics g) {
		super.paint(g);

		// Graphics2D g2d = (Graphics2D) g;

		for (int j = -1500; j < 6000; j += 1200) {
			g.drawImage(background, j - camera, 0, null);
		}

		g.drawImage(end, -3000 - camera, 0, 1800, 180, null);

		for (int i = -1500; i < 6000; i++) {
			if (i % 24 == 0) {
				g.drawImage(checkerboard, i - camera, getHeight() - 24, 24, 24, null);
			}
		}

		for (Door d : doors) {
			g.setColor(d.c);
			g.fillRect(d.pos - camera + 96, getHeight() - 120, 96, 96);
			g.drawImage(door1, d.pos - camera + 96, getHeight() - 120, 96, 96, null);
			g.setColor(Color.red);
			// g2d.draw(d.getCollisionRect(camera, getHeight()));
		}

		for (Forewarner f : forewarners) {
			g.setColor(Color.yellow);
			g.drawImage(forewarnerAnimation.getCurrentFrame(), f.pos - camera, getHeight() - 102, 48, 96, null);
			// g2d.draw(f.getCollisionRectangle(camera, getHeight()));
		}

		// System.out.println(camera);

		// g.setColor(Color.blue);
		g.drawImage(playerAnimation.getCurrentFrame(), 30, getHeight() - 102, 48, 96, null);
		// g.fillRect(30, getHeight() - 54, 24, 48);
		// g.setColor(Color.red);
		// g2d.draw(playerRect);
	}

	class Keyboard extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			keys[e.getKeyCode()] = true;
			if (e.getKeyCode() == KeyEvent.VK_W) {
				playerAnimation.startAnimation();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_D) {
				
			}
		}

		public void keyReleased(KeyEvent e) {
			keys[e.getKeyCode()] = false;
			if (e.getKeyCode() == KeyEvent.VK_W) {
				playerAnimation.stopAnimation();
			}
		}
	}

	class Mouse extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			for (Door d : doors) {
				if (new Rectangle(e.getX(), e.getY(), 32, 32).intersects(d.getCollisionRect(camera, getHeight()))
						&& playerRect.intersects(d.getCollisionRect(camera, getHeight()))) {
					if (d.exit) {
						JOptionPane.showMessageDialog(null, "You see freedom beyond the door.", "Aeternum",
								JOptionPane.PLAIN_MESSAGE);
						gameover = true;
						playerAnimation.stopAnimation();
						playerAnimation.startAnimation();
						camera = -2770;
					} else {
						JOptionPane.showMessageDialog(null, "The door leads to nowhere.", "Aeternum",
								JOptionPane.PLAIN_MESSAGE);
					}
				}
			}

			for (Forewarner f : forewarners) {
				if (new Rectangle(e.getX(), e.getY(), 32, 32).intersects(f.getCollisionRectangle(camera, getHeight()))
						&& playerRect.intersects(f.getCollisionRectangle(camera, getHeight()))) {
					JOptionPane.showMessageDialog(null, f.getStatement(doors), "Aeternum", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
}
