package observer3;

import java.awt.*;

public class GreenBall extends Ball {
	public GreenBall(Color color, int xSpeed, int ySpeed, int ballSize) {
		super(color, xSpeed, ySpeed, ballSize);
	}

	@Override
	public void change(char key) {
		this.setXSpeed(-1 * this.getXSpeed());
		this.setYSpeed(-1 * this.getYSpeed());
	}
}
