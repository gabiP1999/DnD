package com.Games;

public class Mage extends Player{
    protected Resource mana;
    protected int manaCost;
    protected int abilityRange;
    protected int spellPower;
    protected int maxHits;
    public Mage(String name, int health, int attackPts, int defensePts,int manapool,int manaCost,int spellPower,int maxHits,int abilityRange) {
        super(name, health, attackPts, defensePts);
        mana=new Resource(manapool/4,manapool);
        this.manaCost=manaCost;
        this.abilityRange=abilityRange;
        this.spellPower=spellPower;
        this.maxHits=maxHits;
    }

    @Override
    public boolean checkResources() {
        boolean ans = mana.getCurrAmount()>=manaCost;
        if(!ans)msg.send("Not enough mana!");
        return ans;
    }

    @Override
    public void castSpecialAbility() {
        mana.decrease(manaCost);
        msg.send(String.format("%s casts Blizzard special ability",getName()));
    }

    @Override
    public int getAbilityRange() {
        return abilityRange;
    }

    @Override
    public int getAbilityHits() {
        return maxHits;
    }

    @Override
    public int getAbilityDamage() {
        return spellPower;
    }

    @Override
    public void processStep() {
        int manaMult=1;
        mana.increase(level*manaMult);
    }
    public void levelUp(){
        super.levelUp();
        int manaPoolMult = 25;
        int spellMult=10;
        int manaMult=4;
        mana.increaseResourcePool(level*manaPoolMult);
        mana.increase(mana.getResourcePool()/manaMult);
        spellPower=spellPower+(spellMult*level);
    }
    public String description(){
        return String.format("%s\t\tMana: %s\t\tSpell power: %d\t\tAbility hits: %d",super.description(),mana.toString(),getAbilityDamage(),getAbilityHits());
    }
}
