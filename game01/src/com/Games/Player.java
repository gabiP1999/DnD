package com.Games;

public abstract class Player extends Unit{
    protected PlayerDeathCallback deathCallback;
    protected int level;
    protected int experience;
    private static final char playerChar='@';
    protected InputProvider inputProvider;


    private final int expMult =50;
    private final int healthMult = 10;
    private final int attackMult=4;

    public Player( String name, int health, int attackPts, int defensePts) {
        super( playerChar, name, health, attackPts, defensePts);
        experience=0;
        level=1;
    }
    public abstract boolean checkResources();
    public abstract void castSpecialAbility();
    public abstract int getAbilityRange();
    public abstract int getAbilityHits();
    public abstract int getAbilityDamage();
    public void getAction() throws Exception {
        inputProvider.getAction();
    }


    public void abilityDamage(Enemy e,int abilityDamage){
        int dmg = Math.max(abilityDamage-e.rollDefense(),0);
        e.getHealth().decrease(dmg);
        msg.send(String.format("%s hit %s for %d ability damage",getName(),e.getName(),dmg));
        if(!e.alive()){
            onKill(e);
        }
    }

    public Player initialize(Position p,MessageCallBack msg,PlayerDeathCallback pdc,InputProvider inputProvider){
        super.initialize(p,msg);
        this.inputProvider=inputProvider;
        this.deathCallback=pdc;
        return this;
    }
    private int levelUpRequirement(){
        return expMult * level;
    }
    public boolean levelUpCheck(){
        return experience>=levelUpRequirement();
    }
    public void levelUp(){
            experience-=level*expMult;
            level=level+1;
            getHealth().increaseResourcePool(level*healthMult);
            getHealth().increase(level*healthMult);
            increaseAttack(level*attackMult);
            increaseDefense(level);
            msg.send("Level Up!");
    }

    @Override
    public void visit(Enemy enemy) {
        super.battle(enemy);
        if(!enemy.alive()){
            swapPosition(enemy);
            onKill(enemy);
        }
    }
    public void onKill(Enemy enemy){
        experience+=enemy.getExpValue();
        enemy.onDeath();
        msg.send(String.format("%s killed %s",getName(),enemy.getName()));
        while(levelUpCheck()){
            levelUp();
        }
    }

    @Override
    public void visit(Player player) {
        return;
    }

    @Override
    public void onDeath() throws Exception {
        msg.send("You Lost.");
        deathCallback.call();
    }

    @Override
    public void accept(Unit t) throws Exception {
        t.visit(this);
    }
    public int getLevel(){return level;}
    public String description() {
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%d", super.description(), getLevel(), getExperience(), levelUpRequirement());
    }

    public int getExperience() {
        return experience;
    }
    public String toString(){
        if(!alive())return "X";
        return super.toString();
    }
}
