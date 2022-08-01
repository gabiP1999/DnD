package com.Games;

public abstract class Tile implements Comparable {
    protected Position position;
    protected char tile;
    public Tile(char c){
        this.tile=c;
    }
    public Tile initialize(Position p){
        position=p;
        return this;
    }
    public double range(Tile other){
        double a = Math.pow(position.getxPos()-other.position.getxPos(),2 );
        double b = Math.pow(position.getyPos()-other.position.getyPos(), 2);
        return Math.sqrt(a+b);
    }
    public String toString(){
        return String.valueOf(tile);
    }
    public char getTile(){return tile;}

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position p){
        position=p;
    }
    public void swapPosition(Tile other){
        Position tmp = this.getPosition();
        setPosition(other.getPosition());
        other.setPosition(tmp);
    }
    public abstract void accept(Unit t) throws Exception;

    @Override
    public int compareTo(Object o) {
        if(o instanceof Tile){
            return getPosition().compareTo(((Tile) o).getPosition());
        }
        return 0;
    }
    public boolean equals(Tile t){
        return position.equals(t.position);
    }
}
