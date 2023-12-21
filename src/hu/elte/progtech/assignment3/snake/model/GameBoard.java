/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.elte.progtech.assignment3.snake.model;

/**
 *
 * @author zelei
 */

import hu.elte.progtech.assignment3.snake.view.Scoreboard;
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

public class GameBoard extends JPanel implements ActionListener {

    private final int WIDTH = 350;
    private final int HEIGHT = 350;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];

    private int dots;
    private static int points = 0;
    private int appleX;
    private int appleY;
    private int[] rockX;
    private int[] rockY;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean game = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    private Image rock;

    public GameBoard() {
        initBoard();
    }
    
    public static int getPoints(){
        return points;
    }
    
    public boolean getGame(){
        return this.game;
    }
    
    protected void initBoard() {

        addKeyListener(new KeyboardAdapter());
        setBackground(Color.orange);
        setFocusable(true);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon dotIcon = new ImageIcon("src/assets/dot.png");
        ball = dotIcon.getImage();

        ImageIcon appleIcon = new ImageIcon("src/assets/apple.png");
        apple = appleIcon.getImage();

        ImageIcon headIcon = new ImageIcon("src/assets/head.png");
        head = headIcon.getImage();
        
        ImageIcon rockIcon = new ImageIcon("src/assets/rock.png");
        rock = rockIcon.getImage();
    }

    private void initGame() {

        dots = 2;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        
        locateApple();
        locateRocks();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        draw(g);
    }
    
    private void draw(Graphics g) {
        if (game) {
            g.drawImage(apple, appleX, appleY, this);
            
            for(int i = 0; i < rockX.length; ++i){
                g.drawImage(rock,rockX[i],rockY[i],this);
            }

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }        
    }

    public void gameOver(Graphics g) {
        
        String msg = "Game Over!";
        Font small = new Font("Garamond", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
        
        Scoreboard s = new Scoreboard();
        s.setVisible(true);
        s.pack();
    }

    private void checkApple() {

        if ((x[0] == appleX) && (y[0] == appleY)) {
            dots++;
            points++;
            locateApple();
        }
    }

    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (left) {
            x[0] -= DOT_SIZE;
        }

        if (right) {
            x[0] += DOT_SIZE;
        }

        if (up) {
            y[0] -= DOT_SIZE;
        }

        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                game = false;
            }
        }
        
        for(int i = 0; i < rockX.length; ++i){
            if(x[0] == rockX[i] && y[0] == rockY[i]){
                game = false;
            }
        }

        if (y[0] >= HEIGHT) {
            game = false;
        }

        if (y[0] < 0) {
            game = false;
        }

        if (x[0] >= WIDTH) {
            game = false;
        }

        if (x[0] < 0) {
            game = false;
        }
        
        if (!game) {
            timer.stop();
        }
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        appleX = (r * DOT_SIZE);

        r = (int) (Math.random() * RAND_POS);
        appleY = (r * DOT_SIZE);
    }
    
    private void locateRocks(){
        int r = (int) (Math.random() * 10) + 1;
        rockX = new int[r];
        rockY = new int[r];
        
        for(int i = 0; i < r; ++i){
            int rnd = (int) (Math.random() * RAND_POS);
            rockX[i] = (rnd * DOT_SIZE);
            rnd = (int) (Math.random() * RAND_POS);
            rockY[i] = (rnd * DOT_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (game) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class KeyboardAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_A) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_D) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_W) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_S) && (!up)) {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}