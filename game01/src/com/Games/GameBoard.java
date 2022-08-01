package com.Games;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameBoard {
    protected List<Tile> tiles;
    public GameBoard(Tile[][] tileArr){
        tiles = Arrays.stream(tileArr).flatMap(Arrays::stream).collect(Collectors.toList());
    }
    public List<Tile> getTiles(){
        return tiles;
    }
    private void sort(){
        tiles.sort(Tile::compareTo);
    }
    public String toString(){
        sort();
        StringBuilder res = new StringBuilder();
        int lineCounter = 0;
        for(Tile t : tiles){
            if(t.getPosition().getxPos()>lineCounter){
                res.append("\n");
                lineCounter++;
            }
            res.append(t.toString());
        }
        return res.toString();
    }

    public void remove(Tile t) {
        tiles.remove(t);
        Empty empty = new Empty();
        empty.initialize(t.getPosition());
        tiles.add(empty);
    }
}
