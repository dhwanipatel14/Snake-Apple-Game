import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.Timer;

/*	
 * The purpose of this class is to create Panel for the Snake to keep track of all his movements
 *  as a subclass of JPanel.
 */
public class SnakeGamePanel extends JPanel implements ActionListener {
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[UNIT_SIZE];
	final int y[] = new int[UNIT_SIZE];
	int bodyParts = 6;
	int applesEaten;
	int applesIn_X;
	int applesIn_Y;
	char direction = 'D';
	boolean running = false;
	Timer timer;
	Random random;
/*
 * Default Constructor of GamePanel
 */
	SnakeGamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
/*
 *  Method to start the Game by creating newapple and setting timer.
 */
	public void startGame() {
		newApples();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
/*
 *  @param g of Graphics Type is used to design snake. 
 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawComponent(g);
	}
	/*
	 * Method to customize grid of the panel
	 */
	public void drawComponent(Graphics g) {
		if (running) {
			
			for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
			}
			//set the color and size of apple	
			g.setColor(Color.red);
			g.fillOval(applesIn_X, applesIn_Y, UNIT_SIZE, UNIT_SIZE);
			
			// Drawing the Body of Snake;
			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(45, 180, 0));
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+ applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);


		}
		else {
			gameOver(g);	
		}
	}

	/*
	 * Method to generate new apples by setting its coordinates	
	 */
	public void newApples() {
		applesIn_X = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		applesIn_Y = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}
/*
 * Method to move the snakes in all the four directions.
 */
	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
	//TO track direction of the snake
		switch (direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}

	}
  //TO get updates if snake has eaten appples or not.
	public void checkApples() {
		if ((x[0] == applesIn_X) && (y[0] == applesIn_Y)) {
			bodyParts++;
			applesEaten++;
			newApples();
		}

	}
/*
 * Method to check that head of the Snake collides with apple
 */
	public void checkCollisions() {

		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;// for game over
			}
		}
		//Conditions to check if head touches the left border
		if (x[0] < 0) {
			running = false;
		}
		//Condition to check if head touches the right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//Condition to check if head touches the top border
		if (y[0] < 0) {
			running = false;
		}
		//Condition to check if head touches the bottom border	
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		//To stop the timer
		if (!running) {
			timer.stop();
		}
	}
	
//Method To print Score and add different color and Font in the game.
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+ applesEaten,(SCREEN_WIDTH - metrics1.stringWidth("Score : "+ applesEaten))/2,g.getFont().getSize());
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over",(SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);

	}

	@Override
	//TO perform to actions of the snakes.
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApples();
			checkCollisions();
		}
		repaint();

	}

	public class MyKeyAdapter extends KeyAdapter {
		//Method to track snake events by getting key
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			
			}

		}

	}

	
}
