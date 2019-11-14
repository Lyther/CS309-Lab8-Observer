package observer3;

import java.awt.*;

public class BlueBall extends Ball {
	public BlueBall(Color color, int xSpeed, int ySpeed, int ballSize) {
		super(color, xSpeed, ySpeed, ballSize);
	}

	@Override
	public void notify(char key) {
		switch (key) {
			case 'a':
				this.setXSpeed(Math.abs(this.getXSpeed()) * -1);
				break;
			case 'd':
				this.setXSpeed(Math.abs(this.getXSpeed()));
				break;
			case 'w':
				this.setYSpeed(Math.abs(this.getYSpeed()) * -1);
				break;
			case 's':
				this.setYSpeed(Math.abs(this.getYSpeed()));
				break;
		}
	}
}
