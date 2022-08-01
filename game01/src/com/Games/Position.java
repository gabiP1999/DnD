package com.Games;

public class Position implements Comparable{
    private int xPos;
    private int yPos;
    public Position(int x, int y){
        if(x<0 | y<0) throw new IllegalArgumentException();
        xPos=x;
        yPos=y;
    }

    public void setxPos(int xPos) {
        if(xPos<0) throw new IllegalArgumentException();
        this.xPos = xPos;
    }
    public void setyPos(int yPos){
        if(yPos<0) throw new IllegalArgumentException();
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }



    @Override
    public int compareTo(Object o) {
        if(o instanceof Position){
            if(getxPos()>((Position) o).getxPos())return 1;
            if(getxPos()<((Position) o).getxPos())return -1;
            if(getyPos()>((Position) o).getyPos())return 1;
            if(getyPos()<((Position) o).getyPos())return -1;
        }
        return 0;
    }
    public boolean equals(Position p){
        return (xPos==p.xPos & yPos==p.yPos);
    }
}
