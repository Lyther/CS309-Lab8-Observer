package observer3;

import java.awt.*;

public class GreenBall extends SubjectBall {
	public GreenBall(Color color, int xSpeed, int ySpeed, int ballSize) {
		super(color, xSpeed, ySpeed, ballSize);
	}

	@Override
	public void notify(char key) {
		this.setXSpeed(-1 * this.getXSpeed());
		this.setYSpeed(-1 * this.getYSpeed());
	}

	@Override
	public boolean panic(Ball b, double dis) {
		if (this.distance(b) < dis) {
			return true;
		}
		return false;
	}
}
