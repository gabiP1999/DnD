package com.Games;

public class Warrior extends Player {
    private final int abilityCooldown;
    protected int remainingCooldown;
    private final int healthMult=5;
    private final int attackMult=2;
    private final int defenseMult=1;
    private final int abilityRange = 3;
    private final int abilityHits = 1;

    public Warrior(String name, int health, int attackPts, int defensePts,int abilityCooldown) {
        super( name, health, attackPts, defensePts);
        this.abilityCooldown=abilityCooldown;
        remainingCooldown=0;
    }
    public void levelUp(){
        super.levelUp();
        remainingCooldown=0;
        getHealth().increaseResourcePool(healthMult*getLevel());
        getHealth().increase(healthMult*getLevel());
        increaseAttack(attackMult*getLevel());
        increaseDefense(defenseMult*getLevel());
    }
    public void processStep(){
        remainingCooldown=Math.max(0,remainingCooldown-1);
    }
    public boolean checkResources(){
        boolean ans =remainingCooldown==0;
        if(!ans)msg.send("Ability cooldown remaining");
        return ans;
    }
    public void castSpecialAbility(){
            remainingCooldown=abilityCooldown;
            health.increase(getDefensePts()*10);
            msg.send(String.format("%s casts Avenger's Shield special ability",getName()));
    }
    public int getAbilityCooldown(){
        return abilityCooldown;
    }
    public int getRemainingCooldown(){
        return remainingCooldown;
    }

    @Override
    public int getAbilityRange() {
        return abilityRange;
    }

    @Override
    public int getAbilityHits() {
        return abilityHits;
    }
    public int getAbilityDamage(){
        return (int)(getHealth().getResourcePool()*0.1);
    }
    public String description(){
        return String.format("%s\t\tAbility cooldown %d/%d",super.description(),getRemainingCooldown(),getAbilityCooldown());
    }

}
