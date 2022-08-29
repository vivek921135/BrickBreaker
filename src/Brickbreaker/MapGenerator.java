package Brickbreaker;

import java.awt.*;

//for creating the bricks and take care of ball touch that brick or not
public class MapGenerator {
    public int map[][];
    public int brickwidth;
    public int brickheight;
    public MapGenerator(int row, int col){
        map= new int[row][col];
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                map[i][j]=1; // 1 will detect that this particular brickhave not been intersected with the ball
            }
        }
        brickwidth=540/col;
        brickheight=150/row;

    }

    public void draw(Graphics2D g){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++) {
              if(map[i][j] >0){
                  g.setColor(Color.cyan); // create the rectangle of all bricks
                  g.fillRect(j*brickwidth+80, i*brickheight+50 , brickwidth,brickheight);

                  g.setStroke(new BasicStroke(3));// for creating the border for bricks
                  g.setColor(Color.GRAY);
                  g.drawRect(j*brickwidth+80, i*brickheight+50 , brickwidth,brickheight);

              }
            }
            }
    }

    public void setBrickValue(int value, int row, int col){
        map[row][col]=value;
    }
}
