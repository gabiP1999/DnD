package com.Games;

import java.util.List;

public class EnemyActions {
    private List<Tile> tiles;
    private Player player;
    public EnemyActions(List<Tile> tiles,Player player){
        this.tiles=tiles;
        this.player=player;
    }
    public void preformAction(Enemy e) throws Exception {
        if(e instanceof Monster)preformAction((Monster) e);
        if(e instanceof Trap)preformAction((Trap)e);
    }
    public void preformAction(Monster monster) throws Exception {
        int vision = monster.getVisionRange();
        if(monster.range(player)<vision){
            int dx = monster.getPosition().getxPos()-player.getPosition().getxPos();
            int dy = monster.getPosition().getyPos()-player.getPosition().getyPos();
            if(Math.abs(dx)<Math.abs(dy)){
                if(dy>0)moveLeft(monster);
                else moveRight(monster);
            }
            else{
                if(dx>0)moveUp(monster);
                else moveDown(monster);
            }
        }
        else{
            moveRandomly(monster);
        }
        monster.processStep();
    }

    private void moveRandomly(Enemy enemy) throws Exception {
        int random = (int)(Math.random()*4);
        if(random==1)moveUp(enemy);
        else if(random==2)moveDown(enemy);
        else if(random==3)moveRight(enemy);
        else if(random==4)moveLeft(enemy);
    }

    private void moveUp(Enemy enemy) throws Exception {
        Position p = new Position(enemy.getPosition().getxPos()-1,enemy.getPosition().getyPos());
        enemy.interact(getTile(p));
    }
    private void moveDown(Enemy enemy) throws Exception {
        Position p = new Position(enemy.getPosition().getxPos()+1,enemy.getPosition().getyPos());
        enemy.interact(getTile(p));
    }
    private void moveRight(Enemy enemy) throws Exception {
        Position p = new Position(enemy.getPosition().getxPos(),enemy.getPosition().getyPos()+1);
        enemy.interact(getTile(p));
    }
    private void moveLeft(Enemy enemy) throws Exception {
        Position p = new Position(enemy.getPosition().getxPos(),enemy.getPosition().getyPos()-1);
        enemy.interact(getTile(p));
    }
    private Tile getTile(Position p){
        for(Tile t : tiles){
            if( t.position.equals(p))
                return t;
        }
        throw new IllegalArgumentException("Tile not found!");
    }
    public void preformAction(Trap trap) throws Exception {
        if(trap.range(player)<trap.getAttackRange()){
            trap.attack(player);
        }
        trap.processStep();
    }
}
