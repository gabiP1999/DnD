package com.Games;

public class Wall extends Tile{

    private static final char wallChar = '#';
    public Wall (){
        super(wallChar);
    }

    @Override
    public void accept(Unit t) {
        t.visit(this);
    }

}
