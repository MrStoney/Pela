package pela;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Taust extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
 /*   private final int RAND_POS = 29;*/
    private final int DELAY = 50;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;
    
    private int wall1_x = 2*DOT_SIZE;
    private int wall1_y = B_HEIGHT-(DOT_SIZE*4);
    
    private int wall2_x = 28*DOT_SIZE;
    private int wall2_y = B_HEIGHT-(DOT_SIZE*2);
    
    private int wall3_x = 20*DOT_SIZE;
    private int wall3_y = 4*DOT_SIZE;
    
    private int wall4_x = 2*DOT_SIZE;
    private int wall4_y = 12*DOT_SIZE;

    private boolean leftDirection = false;
    private boolean rightDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private boolean moving = false;
    private boolean win = false;
    
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public Taust() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("head.png");
        head = iih.getImage();
    }

    private void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (inGame && !win) {

            g.drawImage(apple, apple_x, apple_y, this);
            
            for(int z=0; z < 10; z++) {
            	g.drawImage(head,  wall1_x+(z*DOT_SIZE),  wall1_y, this);
            	
            }
            
            for(int z=0; z < 10; z++) {
            	g.drawImage(ball,  wall2_x,  wall2_y-(z*DOT_SIZE), this);
            	
            }
            
            for(int z=0; z < 10; z++) {
            	g.drawImage(ball,  wall3_x+(z*DOT_SIZE),  wall3_y, this);
            	
            }
            
            for(int z=0; z < 10; z++) {
            	g.drawImage(ball,  wall4_x,  wall4_y-(z*DOT_SIZE), this);
            	
            }
            
            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else if (!win) {

            gameOver(g);
        } else {
        	youWin(g);
        }
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }
    
    private void youWin(Graphics g) {
        
        String msg = "YOU WIN!";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

       /*     dots++;
            locateApple();*/
        	win = true;
            leftDirection = false;
            upDirection = false;
            downDirection = false;
            rightDirection = false;
            moving = false;
        }
    }
    
    private void checkWall() {
    	if ((x[0] >= wall1_x) && (x[0]<= wall1_x+(10*DOT_SIZE)) && (y[0] == wall1_y-DOT_SIZE) && downDirection) {
            leftDirection = false;
            upDirection = false;
            downDirection = false;
            rightDirection = false;
            moving = false;
            wall1_x = wall1_x + 10*DOT_SIZE;
    	}
    	
    	if ((x[0] == wall2_x-DOT_SIZE) && (y[0] <= wall2_y-DOT_SIZE) && (y[0]>= wall2_y-(10*DOT_SIZE) && rightDirection)) {
            leftDirection = false;
            upDirection = false;
            downDirection = false;
            rightDirection = false;
            moving = false;
    	}
    	
    	if ((x[0] >= wall3_x) && (x[0]<= wall3_x+(10*DOT_SIZE)) && (y[0] == wall3_y+DOT_SIZE) && upDirection) {
            leftDirection = false;
            upDirection = false;
            downDirection = false;
            rightDirection = false;
            moving = false;
    	}
    	if ((x[0] == wall4_x+DOT_SIZE) && (y[0] <= wall4_y-DOT_SIZE) && (y[0]>= wall4_y-(10*DOT_SIZE) && leftDirection)) {
            leftDirection = false;
            upDirection = false;
            downDirection = false;
            rightDirection = false;
            moving = false;
    	}
    }

    private void move() {
    	if (leftDirection || rightDirection || upDirection || downDirection) {
    		for (int z = dots; z > 0; z--) {
            	x[z] = x[(z - 1)];
            	y[z] = y[(z - 1)];
        	}
    	}
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }
        
        if(!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {
/*
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));*/
    	
    	apple_x = 3*DOT_SIZE;
    	apple_y = B_HEIGHT-2*DOT_SIZE;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();
            checkWall();
            checkCollision();
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection) && !moving) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
                moving = true;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection) && !moving) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
                moving = true;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection) && !moving) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
                moving = true;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection) && !moving) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
                moving = true;
            }
        }
    }
}