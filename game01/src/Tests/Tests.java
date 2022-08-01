package Tests;

import com.Games.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Tests {
    private Warrior warrior = new Warrior("Warrior",100000,10000,0,100);
    private Mage mage = new Mage("Mage",100000,10000,0,100,100,100,10,10);
    private Trap trap = new Trap('T',"Trap",100000,10000,0,20,1,1);
    private Monster monster = new Monster('M',"Monster",100000,10000,0,50,10);
    private int deadPlayers=0;
    private Empty empty = new Empty();
    private Wall wall = new Wall();
    private List<String> messages = new ArrayList<>();
    private int deadEnemies=0;
    private InputProvider inputProvider = new InputProvider() {
        @Override
        public void getAction() throws Exception {
            return;
        }
    };
    private PlayerDeathCallback playerDeathCallback= new PlayerDeathCallback() {
        @Override
        public void call() throws Exception {
            deadPlayers++;
        }
    };
    private EnemyDeathCallBack enemyDeathCallBack = new EnemyDeathCallBack() {
        @Override
        public void call() {
            deadEnemies++;
        }
    };
    private MessageCallBack messageCallBack = new MessageCallBack() {
        @Override
        public void send(String s) {
            return;
        }
    };
    private Position position = new Position(1,1);
    public Tests(){
        warrior.initialize(position,messageCallBack,playerDeathCallback,inputProvider);
        mage.initialize(position,messageCallBack,playerDeathCallback,inputProvider);
        trap.initialize(position,messageCallBack,enemyDeathCallBack);
        monster.initialize(position,messageCallBack,enemyDeathCallBack);
        empty.initialize(new Position(1,2));
        wall.initialize(new Position(1,2));
    }

    @Test
    public void PlayerAttackPlayer(){

        try {
            warrior.interact(mage);
            Assert.assertEquals(100000,mage.getHealth().getCurrAmount());
        }
        catch (Exception e){
            Assert.fail();
        }
    }
    @Test
    public void EnemyAttackEnemy(){
        try{
            monster.interact(trap);
            Assert.assertEquals(100000,trap.getHealth().getCurrAmount());
        }
        catch (Exception e){
            Assert.fail();
        }
    }
    @Test
    public void PlayerAttackEnemy(){
        try{
            warrior.interact(monster);
            Assert.assertNotEquals(100000,monster.getHealth().getCurrAmount());
        }
        catch (Exception e){
            Assert.fail();
        }
    }
    @Test
    public void EnemyAttackPlayer(){
        try{
            monster.interact(warrior);
            Assert.assertNotEquals(100000,warrior.getHealth().getCurrAmount());
        }
        catch (Exception e){
            Assert.fail();
        }
    }
    @Test
    public void PlayerKillEnemy(){
        try{
            while (monster.alive()){
                warrior.interact(monster);
            }


            Assert.assertEquals(1,deadEnemies);
        }
        catch (Exception e){
            Assert.fail();
        }
    }
    @Test
    public void PlayerLevelUp(){
        warrior.levelUp();
        Assert.assertEquals(2,warrior.getLevel());
    }
    @Test
    public void PlayerSpecialAbility(){
        warrior.abilityDamage(trap,10000000);
        Assert.assertFalse(trap.alive());
    }








}
