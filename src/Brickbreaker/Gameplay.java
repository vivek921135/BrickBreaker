package Brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import javax.swing.Timer;
//import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;  // few properties
    private int score =0;   // starting score is 0

    private int totalBricks=21; // total no. of bricks in the starting
    private Timer timer; // speed of ball
    private int delay=8;

    private int playerX=310; // starting position of the slider

    private int ballposX =120; // starting position of ball
    private int ballposY=350; // take y value from above ...upper boundary y=0
    private int ballXdir=-1 ; //just set the direction of the ball
    private int ballYdir=-2;

    private MapGenerator map;

    public Gameplay(){
        map=new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer= new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //drawing map
        map.draw((Graphics2D) g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //Scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD,25));
        g.drawString(""+score,590,30); //position where score is written

        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);

        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);

        // if player win the game completely
        if(totalBricks<=0){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("You Won : ",260,300);

            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);
        }

        // if game is over and player is out
        if(ballposY>570){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Game Over, Scores : "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);

        }

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir= -ballYdir;
            }
            // finding the intersection of the ball with bricks
           A: for(int i=0;i<map.map.length;i++){ //first map is the variable which we created above in the Gameplay(object)
                for(int j=0;j<map.map[0].length;j++){ // ans second map is of 2d matrix
                    if(map.map[i][j]>0){
                        //we need to first detect the position of ball and the position of brick wrt width and height of that brick
                        int brickX=j* map.brickwidth+80;
                        int brickY=i*map.brickheight +50;
                        int brickwidth=map.brickwidth;
                        int brickheight=map.brickheight;

                        Rectangle rect= new Rectangle(brickX,brickY,brickwidth,brickheight);
                        Rectangle ballRect= new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect=rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;

                            //condition when intersect with left side or right side
                            if(ballposX+19<=brickRect.x ||ballposX+1>=brickRect.x +brickRect.width){
                                ballXdir= -ballXdir;
                            }
                            else{
                                ballYdir= -ballYdir;
                            }
                            break A;// to come out from both i & j loop
                        }
                    }
                }
            }


          // ball moving condition
            ballposX+= ballXdir;
            ballposY+= ballYdir;
            if(ballposX<0){
                ballXdir = -ballXdir;
            }
            if(ballposY<0){
                ballYdir = -ballYdir;
            }
            if(ballposX>670){
                ballXdir = -ballXdir;
            }
        }

        repaint(); // recall the paint method and draw each and every thing again

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX>=600){
                playerX=600;
            }
            else{
                moveRight();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX<10){
                playerX=10;
            }
            else{
                moveLeft();
            }

        }
        // detect the enter event when we want to restart the game
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                ballposX=120;
                ballposY=350;
                ballXdir=-1;
                ballYdir=-2;
                playerX=310;
                score=0;
                totalBricks=21;
                map= new MapGenerator(3,7);

                repaint();
            }
        }

    }

    public void moveRight(){
        play=true;
        playerX+=20; // if we press right then it will move 20 pixels to the right side
    }

    public void moveLeft(){
        play=true;
        playerX-=20; // if we press right then it will move 20 pixels to the right side
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
