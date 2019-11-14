package observer2;

import java.awt.*;

public class RedBall extends Ball {
	public RedBall(Color color, int xSpeed, int ySpeed, int ballSize) {
		super(color, xSpeed, ySpeed, ballSize);
	}

	public void notify(char key) {
		if (key == 'a' || key == 'd') {
			int temp = this.getXSpeed();
			this.setXSpeed(this.getYSpeed());
			this.setYSpeed(temp);
		}
	}
}
