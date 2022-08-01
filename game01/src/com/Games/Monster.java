package com.Games;

public class Monster extends Enemy {
    protected int visionRange;
    public Monster(char c, String name, int health, int attackPts, int defensePts, int expVal,int visionRange) {
        super(c, name, health, attackPts, defensePts, expVal);
        this.visionRange=visionRange;
    }
    public int getVisionRange(){
        return visionRange;
    }
}
