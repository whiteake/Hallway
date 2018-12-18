import javax.swing.JFrame;

public class Game extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 192);
		setTitle("Aeternum");
		add(new Render());
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
