package com.Games;

public class Enemy extends Unit{
    protected EnemyDeathCallBack deathCallBack;
    protected int expValue;
    public Enemy( char c, String name, int health, int attackPts, int defensePts,int expVal) {
        super( c, name, health, attackPts, defensePts);
        if(expVal<1)throw new IllegalArgumentException("exp value has to be more than 0!");
        expValue=expVal;
    }

    public Enemy initialize(Position p, MessageCallBack msg, EnemyDeathCallBack edcb){
        super.initialize(p,msg);
        deathCallBack=edcb;
        return this;
    }

    @Override
    public void processStep() {
        return;
    }

    @Override
    public void visit(Enemy enemy) {
        return;
    }
    public int getExpValue(){
        return expValue;
    }

    @Override
    public void visit(Player player) throws Exception {
        super.battle(player);
        if(!player.alive()){
            msg.send(String.format("%s killed %s",getName(),player.getName()));
            player.onDeath();
        }
    }

    @Override
    public void onDeath() {
        deathCallBack.call();
    }

    @Override
    public void getAction() {

    }

    @Override
    public void accept(Unit t) {
        t.visit(this);
    }
}
