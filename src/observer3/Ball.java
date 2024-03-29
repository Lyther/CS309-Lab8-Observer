package observer3;

import java.awt.*;

public abstract class Ball {
	private Color color;
	private int x, y;
	private int xSpeed, ySpeed;
	private int ballSize;
	private boolean visible;

	public Ball(Color color, int xSpeed, int ySpeed, int ballSize) {
		this.color = color;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.ballSize = ballSize;

		this.visible = true;
		this.x = (int) (Math.random() * 600);
		this.y = (int) (Math.random() * 600);
	}

	public void setColor(Color newColor) {
		this.color = newColor;
	}

	public Color getColor() {
		return this.color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public int getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getBallSize() {
		return ballSize;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void draw(Graphics g) {
		if (isVisible()) {
			g.setColor(this.getColor());
			g.fillOval(this.getX(), this.getY(), this.getBallSize(), this.getBallSize());
		}
	}

	public void move() {
		int newX = this.getX() + this.getXSpeed();
		int newY = this.getY() + this.getYSpeed();

		this.setX(newX);
		this.setY(newY);

		this.valid(newX, newY);
	}

	public void valid(int X, int Y) {
		if (X <= 0) {
			this.setXSpeed(Math.abs(getXSpeed()));
		} else if (X >= 600 - this.getBallSize()) {
			this.setXSpeed(-1 * Math.abs(getXSpeed()));
		}

		if (Y <= 0) {
			this.setYSpeed(Math.abs(getYSpeed()));
		} else if (Y > 600 - this.getBallSize()) {
			this.setYSpeed(-1 * Math.abs(getYSpeed()));
		}
	}

	public boolean isIntersect(Ball b) {
		double dis = (this.getBallSize() + b.getBallSize()) / 2.0;
		return this.distance(b) < dis;
	}

	public double distance(Ball b) {
		int diffX = this.getX() - b.getX();
		int diffY = this.getY() - b.getY();
		return Math.sqrt(diffX * diffX + diffY * diffY);
	}

	public void shift(int diffX, int diffY) {
		int newX = this.getX() + diffX;
		int newY = this.getY() + diffY;

		newX = Math.max(0, newX);
		newX = Math.min(600 - this.getBallSize(), newX);
		newY = Math.max(0, newY);
		newY = Math.min(600 - this.getBallSize(), newY);

		this.setX(newX);
		this.setY(newY);
	}

	abstract public void notify(char key);
}