package com.Games;

public abstract class Unit extends Tile {
    protected static final RandomGenerator randomGenerator=RandomGenerator.getInstance();
    protected String name;
    protected Resource health;
    protected int attackPts;
    protected int defensePts;
    protected MessageCallBack msg;
    public Unit(char c,String name,int health,int attackPts,int defensePts) {
        super(c);
        this.name=name;
        this.health=new Resource(health,health);
        this.attackPts=attackPts;
        this.defensePts=defensePts;
    }
    public Unit initialize(Position p, MessageCallBack msg){
        super.initialize(p);
        this.msg=msg;
        return this;
    }
    public void interact(Tile other) throws Exception {
        other.accept(this);
    }
    public abstract void processStep();

    public String getName() {
        return name;
    }
    public abstract void visit(Enemy enemy);
    public abstract void visit(Player player) throws Exception;
    public void increaseAttack(int amount){
        amountCheck(amount);
        attackPts+=amount;
    }
    public void increaseDefense(int amount){
        amountCheck(amount);
        defensePts+=amount;
    }
    public int rollAttack(){
        int res = randomGenerator.random(0,attackPts);
        msg.send(String.format("%s rolled %d attack points",getName(),res));
        return res;
    }
    public int rollDefense(){
        int res = randomGenerator.random(0,defensePts);
        msg.send(String.format("%s rolled %d defense points",getName(),res));
        return res;
    }
    public void battle(Unit u){
        msg.send(String.format("%s engaged in combat with %s\n%s\n%s",getName(),u.getName(),description(),u.description()));
        int attack = rollAttack();
        int defense = u.rollDefense();
        int dmg = Math.max(attack-defense,0);
        u.health.decrease(dmg);
        msg.send(String.format("%s dealt %d damage to %s",getName(),dmg,u.getName()));

    }
    public String description(){
        return String.format("%s\t\tHealth: %s\t\tAttack:%d\t\tDefense:%d",getName(),health.toString(),attackPts,defensePts);
    }

    private void amountCheck(int amount){
        if(amount<0)throw new IllegalArgumentException("Amount can't be negative!");
    }
    public boolean alive(){
        return health.getCurrAmount()>0;
    }

    public void visit(Empty empty){
        swapPosition(empty);
    }
    public void visit(Wall wall){
        return;
    }
    public abstract void onDeath() throws Exception;
    public Resource getHealth(){
        return health;
    }
    public abstract void getAction() throws Exception;
    public int getAttackPts(){return attackPts;}
    public int getDefensePts(){return defensePts;}
}
