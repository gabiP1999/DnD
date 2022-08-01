package com.Games;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PlayerActions implements InputProvider {
    private Player player;
    private List<Enemy> enemies;
    private List<Tile> tiles;
    public PlayerActions (){
    }
    public PlayerActions initialize(List<Enemy> enemies,List<Tile> tiles){
        this.enemies=enemies;
        this.tiles=tiles;
        return this;
    }
    public Player getPlayer(){return player;}
    public PlayerActions setPlayer(Player p){
        this.player=p;
        return this;
    }
    private Tile getTile(Position p){
        for(Tile t : tiles){
            if( t.position.equals(p))
                return t;
        }
        throw new IllegalArgumentException("Tile not found!");
    }
    private void specialAbility(int abilityDmg,int maxRange,int maxHits){
        if(maxHits<0 | maxRange<0)throw new IllegalArgumentException("Range and Hits can't be negative");
        if(maxHits==0 & maxRange==2){
            specialAbilityRogue(abilityDmg,maxRange);
            return;
        }
        if(player.checkResources()){
            List<Enemy> enemiesCopy = new ArrayList<>(enemies);
            player.castSpecialAbility();
            int enemiesInRange = 0;
            boolean firstIteration=true;
            while((maxHits>0)&(firstIteration | enemiesInRange>0)){
                enemiesInRange = 0;
                firstIteration=false;

                for(Enemy e : enemiesCopy){
                    if(player.range(e)<maxRange){
                        if(e.alive()) {
                            player.abilityDamage(e, abilityDmg);
                            enemiesInRange++;
                            maxHits -= 1;
                        }
                    }
                }
            }
        }
    }

    private void specialAbilityRogue(int abilityDmg,int maxRange) {
        if(player.checkResources()){
            List<Enemy> enemiesCopy = new ArrayList<>(enemies);
            player.castSpecialAbility();
            for(Enemy e : enemiesCopy){
                if(player.range(e)<maxRange){
                    if(e.alive()){
                        player.abilityDamage(e,abilityDmg);
                    }
                }
            }
        }
    }

    @Override
    public void getAction() throws Exception {
        Position position = player.getPosition();
        String w = "w";
        String s = "s";
        String d = "d";
        String a = "a";
        String e = "e";
        String q = "q";
        Scanner scanner = new Scanner(System.in);
        String key = scanner.nextLine();
        if(key.equals(w)){
            Position p= new Position(position.getxPos()-1, position.getyPos());
            player.interact(Objects.requireNonNull(getTile(p)));
            player.processStep();
        }
        else if(key.equals(s)){
            Position p= new Position(position.getxPos()+1, position.getyPos());
            player.interact(Objects.requireNonNull(getTile(p)));
            player.processStep();
        }
        else if(key.equals(a)){
            Position p= new Position(position.getxPos(), position.getyPos()-1);
            player.interact(Objects.requireNonNull(getTile(p)));
            player.processStep();
        }
        else if(key.equals(d)){
            Position p= new Position(position.getxPos(), position.getyPos()+1);
            player.interact(Objects.requireNonNull(getTile(p)));
            player.processStep();
        }
        else if(key.equals(q)){
            player.processStep();
            return;
        }
        else if(key.equals(e)){
            specialAbility(player.getAbilityDamage(), player.getAbilityRange(), player.getAbilityHits());
            player.processStep();
        }
        else{
            getAction();
        }
    }
}
