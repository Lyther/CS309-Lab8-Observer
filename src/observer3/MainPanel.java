package observer3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends JPanel implements KeyListener {

	private List<Ball> paintingBallList = new ArrayList<>();
	private List<Ball> observerBallList = new ArrayList<>();
	private List<SubjectBall> subjectBallList = new ArrayList<>();
	private boolean start = false;
	private int score = 0;
	private Ball redBall;
	private SubjectBall greenBall;
	private Ball blueBall;


	public MainPanel() {
		redBall = new RedBall(Color.RED, 3, 10, 50);
		greenBall = new GreenBall(Color.GREEN, 5, 7, 100);
		blueBall = new BlueBall(Color.BLUE, 8, 10, 80);

		paintingBallList.add(greenBall);
		paintingBallList.add(redBall);
		paintingBallList.add(blueBall);

		observerBallList.add(redBall);
		observerBallList.add(blueBall);
		subjectBallList.add(greenBall);

		addKeyListener(this);
		setFocusable(true);
		setPreferredSize(new Dimension(600, 600));

		Timer t = new Timer(20, e -> moveBalls());
		t.start();
	}

	public void setPaintingBallList(List<Ball> paintingBallList) {
		this.paintingBallList = paintingBallList;
	}

	public void moveBalls() {
		for (int i = 0; start && i < paintingBallList.size(); i++) {
		    Ball b = paintingBallList.get(i);
			b.move();
		}

		// collision detection
		for (int i = 0; i < paintingBallList.size(); i++) {
		    Ball current = paintingBallList.get(i);
			if (current.isVisible()) {
				for (int j = i + 1; j < paintingBallList.size(); j++) {
					Ball ball = paintingBallList.get(j);
					if (ball.isVisible() && current.isIntersect(ball))
						ball.setVisible(false); // collision occurs
				}
			}
			if ((current.getColor() == Color.RED || current.getColor() == Color.BLUE) && !current.isVisible()) {
			    for (Ball ball : paintingBallList)
			        ball.setVisible(false);
			    break;
            }
		}

		// shift detection
		for (Ball fetch : paintingBallList) {
			if (fetch.getColor() == Color.GREEN) {
				for (Ball current : paintingBallList) {
                    int negativeX = current.getX() - fetch.getX() < 0 ? -1 : 1;
                    int negativeY = current.getY() - fetch.getY() < 0 ? -1 : 1;
					if (current.getColor() == Color.RED && current.distance(fetch) < 100) {
						current.shift(negativeX * 50, negativeY * 50);
					} else if (current.getColor() == Color.BLUE && current.distance(fetch) < 120) {
						current.shift(negativeX * 30, negativeY * 30);
					}
				}
				break;
			}
		}
		for (SubjectBall subject : subjectBallList) {
			for (Ball observer : observerBallList) {
				int negativeX = observer.getX() - subject.getX() < 0 ? -1 : 1;
				int negativeY = observer.getY() - subject.getY() < 0 ? -1 : 1;
				if (observer.getColor() == Color.RED && subject.panic(observer, 100)) {
					observer.shift(negativeX * 50, negativeY * 50);
				} else if (observer.getColor() == Color.BLUE && subject.panic(observer, 120)) {
					observer.shift(negativeX * 30, negativeY * 30);
				}
			}
		}

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int visibleNum = 0;

		for (Ball b : paintingBallList) {
			if (b.isVisible()) {
				b.draw(g);
				visibleNum++;
			}
		}

		if (!start && visibleNum > 1) {
		    g.setFont(new Font("Arial", Font.PLAIN, 48));
		    g.setColor(Color.GRAY);
		    g.drawString("Press Space to Play", 80, 100);
        }

		if (start && visibleNum <= 1) {
		    for (Ball ball : paintingBallList)
		        ball.setVisible(false);
			g.setFont(new Font("Arial", Font.PLAIN, 75));
			for (int i = 70; i < 600; i += 100) {
				g.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
				g.drawString("Game Over!", 100, i);
			}
		} else if (start) {
			score += visibleNum;
		}

		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.setColor(Color.BLACK);
		g.drawString("Score: " + score, 20, 40);

		this.setBackground(Color.WHITE);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		char keyChar = keyEvent.getKeyChar();

		if (keyChar == ' ')
			start = !start;

		for (Ball ball : observerBallList)
			ball.notify(keyChar);
		for (Ball ball : subjectBallList)
			ball.notify(keyChar);
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
	}
}
