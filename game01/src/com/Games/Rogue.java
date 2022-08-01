package com.Games;

public class Rogue extends Player{
    protected final int initialEnergy=100;
    protected final int abilityHits = 0;
    protected final int abilityRange = 2;
    protected Resource energy;
    protected int energyCost;
    public Rogue(String name, int health, int attackPts, int defensePts,int energyCost) {
        super(name, health, attackPts, defensePts);
        energy= new Resource(initialEnergy,initialEnergy);
        this.energyCost=energyCost;
    }

    @Override
    public boolean checkResources() {
        boolean ans = energy.getCurrAmount()>=energyCost;
        if(!ans)msg.send("Not enough energy!");
        return ans;
    }

    @Override
    public void castSpecialAbility() {
        energy.decrease(energyCost);
        msg.send(String.format("%s casts Fan Of Knives special ability",getName()));
    }

    @Override
    public int getAbilityRange() {
        return abilityRange;
    }

    @Override
    public int getAbilityHits() {
        return abilityHits;
    }

    @Override
    public int getAbilityDamage() {
        return attackPts;
    }

    @Override
    public void processStep() {
        int energyBonus=10;
        energy.increase(energyBonus);
    }
    public void levelUp(){
        int eneryBonus = 100;
        int attackBonus = 3;
        super.levelUp();
        energy.increase(eneryBonus);
        increaseAttack(attackBonus*level);
    }
    public String description(){
        return String.format("%s\t\tEnergy: %s",super.description(),energy.toString());
    }
}
