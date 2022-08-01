package com.Games;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileParser{
    private TileFactory tileFactory= new TileFactory();


    public GameLevel parseLevel(File levelFile) throws Exception {
        Scanner scanner = new Scanner(levelFile);
        int lineLength=0;
        int lineCounter=0;
        List<String> lines= new ArrayList<>();
        while(scanner.hasNextLine()){
            String s= scanner.nextLine();
            if(lineCounter==0)lineLength=s.length();
            if(lineCounter>0 & lineLength!=s.length())
                throw new Exception("Lines are not the same length!");
            lines.add(s);
            lineCounter= lineCounter+1;
        }
        char[][] arr =new char[lineLength][lineCounter];
        int i=0;
        for(String line : lines){
            for(int index = 0;index<line.length();index++){
                arr[index][i]=line.charAt(index);
            }
            i++;
        }
        GameLevel gameLevel = new GameLevel();
        int idx=-1;
        if(levelFile.getName().equals("level1.txt")){
            idx= getUserInput(tileFactory.listPlayers());
        }


        Tile[][] tiles = new Tile[lineLength][lineCounter];
        List<Enemy> enemies= new ArrayList<>();
        PlayerActions inputProvider =new PlayerActions();
        for(int x=0;x<arr.length;x++){
            for(int y=0;y<arr[x].length;y++){
                if(arr[x][y]=='.'){
                    tiles[x][y]= tileFactory.produceEmpty(new Position(y,x));
                }
                else if(arr[x][y]=='#'){
                    tiles[x][y]= tileFactory.produceWall(new Position(y,x));
                }
                else if(arr[x][y]=='@'){
                    Player player= tileFactory.producePlayer(idx);
                    MessageCallBack msg = System.out::println;
                    PlayerDeathCallback pDeath = gameLevel::onPlayerDeath;
                    inputProvider.setPlayer(player);
                    player.initialize(new Position(y,x),msg,pDeath,inputProvider);
                    tiles[x][y]=player;
                }
                else if("skqBQzbgwD".indexOf(arr[x][y])>=0){
                    char c = arr[x][y];
                    MessageCallBack msg = System.out::println;
                    Enemy enemy= tileFactory.produceEnemy(c,new Position(y,x),msg,gameLevel);
                    tiles[x][y]=enemy;
                    enemies.add(enemy);
                }
                else{
                    tiles[x][y]= tileFactory.produceEmpty(new Position(y,x));
                }
            }
        }
        GameBoard gameBoard = new GameBoard(tiles);
        inputProvider.initialize(enemies,gameBoard.getTiles());
        gameLevel.initialize(gameBoard, inputProvider.getPlayer(), enemies);
        return gameLevel;
    }

    private int getUserInput(List<Player> players) {
        Scanner scanner = new Scanner(System.in);
        for(Player player : players){
            System.out.println(player.description());
            System.out.println("Enter "+players.indexOf(player)+" to select");
        }
        int ans = scanner.nextInt();
        return ans;
    }
}
