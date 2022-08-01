package com.Games;

public class Empty extends Tile{
    private static final char emptyChar = '.';
    public Empty() {
        super(emptyChar);
    }

    @Override
    public void accept(Unit t) {
        t.visit(this);
    }
}
