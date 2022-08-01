package com.Games;

public class Trap extends Enemy {
    protected boolean visible;
    protected int visibilityTime;
    protected int invisibilityTime;
    protected int tickCount;
    protected final int attackRange = 2;
    public Trap(char c, String name, int health, int attackPts, int defensePts, int expVal,int visibility,int invisibility) {
        super(c, name, health, attackPts, defensePts, expVal);
        visibilityTime=visibility;
        invisibilityTime=invisibility;
        visible=true;
        tickCount=0;
    }
    public int getAttackRange(){
        return attackRange;
    }
    public void attack(Player player) throws Exception {
        int dmg = Math.max(0,rollAttack()-player.rollDefense());
        player.getHealth().decrease(dmg);
        msg.send(String.format("%s dealt %d damage to %s",getName(),dmg,player.getName()));
        if(!player.alive()){
            player.onDeath();
        }
    }
    public String toString(){
        if(visible)return super.toString();
        else return String.valueOf('.');
    }
    public void processStep(){
        tickCount+=1;
        if(visible){
            if(tickCount>=visibilityTime){
                tickCount=0;
                visible=false;
            }
        }
        else {
            if(tickCount>=invisibilityTime){
                tickCount=0;
                visible=true;
            }
        }
    }

}
