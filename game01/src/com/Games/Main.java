package com.Games;


import java.io.File;


public class Main {


    public static void main(String[] args)  {
        if(args==null || (args[0]==null)){
            System.out.println("Missing directory path");
        }
        else {
            try {
                File levels_dir = new File(args[0]);
                LevelManager levelManager = new LevelManager(levels_dir);
                levelManager.getLevelFiles();
                levelManager.startGame();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }


}
