import javax.swing.JFrame;

/*
 *  The purpose of this class is to create a Frame for the game as a subclass of JFrame.
 */
public class SnakeGameFrame extends JFrame{
	/*
	 * Default Constructor of GameFrame
	 */
	SnakeGameFrame(){
		SnakeGamePanel panel = new SnakeGamePanel();
		this.add(panel);
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

}
