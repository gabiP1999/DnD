package com.Games;

import java.util.ArrayList;
import java.util.List;

public class GameLevel implements LevelSubject {
        private boolean isDone;
        private GameBoard gameBoard;
        private Player player;
        private List<Enemy> enemies;
        private EnemyActions enemyActions;
        private List<LevelObserver> observers = new ArrayList<>();
        public GameLevel initialize(GameBoard gameBoard, Player player, List<Enemy> enemies){
                this.gameBoard=gameBoard;
                this.enemies=enemies;
                this.player=player;
                enemyActions= new EnemyActions(gameBoard.getTiles(),player);
                isDone=false;
                return this;
        }
        public void register(LevelObserver o){
                observers.add(o);
        }
        public void unregister(LevelObserver o){
                observers.remove(o);
        }

        public void onPlayerDeath() throws Exception {

                for(LevelObserver o : observers)
                        o.playerDead();
                isDone=true;
        }

        public void onEnemyDeath(Enemy e) throws Exception {
                gameBoard.remove(e);
                enemies.remove(e);
                if(enemies.isEmpty()){
                        isDone=true;
                }
        }

        @Override
        public String toString() {
            return String.format("%s\n%s", gameBoard, player.description());
        }
        public boolean isDone(){return isDone;}

        public void run() throws Exception {
                        player.getAction();
                        for (Enemy e : enemies)
                                enemyActions.preformAction(e);
        }
}
