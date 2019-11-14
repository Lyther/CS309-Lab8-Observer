package observer2;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MainPanel extends JPanel implements KeyListener {

    private List<Ball> paintingBallList = new ArrayList<>();
    private List<Ball> observerBallList = new ArrayList<>();
    private boolean start = false;
    private int score = 0;
    private Ball redBall;
    private Ball greenBall;
    private Ball blueBall;


    public MainPanel() {
        redBall = new RedBall(Color.RED, 3, 10, 50);
        greenBall = new GreenBall(Color.GREEN, 5, 7, 100);
        blueBall = new BlueBall(Color.BLUE, 8, 10, 80);

        paintingBallList.add(redBall);
        paintingBallList.add(greenBall);
        paintingBallList.add(blueBall);

        observerBallList.add(redBall);
        observerBallList.add(greenBall);
        observerBallList.add(blueBall);

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
        for (Ball b : observerBallList) {
            b.move();
        }

        // collision detection
        for (int i = 0; start && i < paintingBallList.size() - 1; i++) {
            if (paintingBallList.get(i).isVisible()) {
                for (int j = i + 1; j < paintingBallList.size(); j++) {
                    Ball ball = paintingBallList.get(j);
                    if (ball.isVisible() && paintingBallList.get(i).isIntersect(ball))
                        ball.setVisible(false); // collision occurs
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

        if (start && visibleNum <= 1) {
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
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
