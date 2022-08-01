package com.Games;

public class RandomGenerator {
    private static RandomGenerator rg = new RandomGenerator();
    public static RandomGenerator getInstance(){
        return rg;
    }
    public int random(int a,int b){
        double res = Math.random()*Math.max(a-b,b-a) + Math.min(a,b);
        return (int)res;
    }
}
