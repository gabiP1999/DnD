package com.Games;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LevelManager implements LevelObserver {
    private List<File> level_files=new ArrayList<>();
    private Path levels_path;
    protected int levelCounter = 0;
    private FileParser fileParser= new FileParser();
    public LevelManager(File levels_dir){
        levels_path=levels_dir.toPath();
    }
    public void getLevelFiles(){
        Path dir =levels_path;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file: stream) {
                File f = file.toFile();
                level_files.add(f);
            }
        } catch (DirectoryIteratorException | IOException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            System.err.println(x);
        }
    }
    public void startGame() throws Exception {
        fileParser= new FileParser();
        levelCounter=0;
        startLevel(0);
    }
    public void startLevel(int index) throws Exception {
        if(level_files.size()<=index){
            System.out.println("You win!");
            restartGame();
        }
        GameLevel level = fileParser.parseLevel(level_files.get(index));
        level.register(this);
        while(!level.isDone()){
            System.out.println(level.toString());
            level.run();
        }
        level.unregister(this);
        System.out.println("Level finished");
        startLevel(index+1);
    }


    @Override
    public void playerDead() throws Exception {
        restartGame();
    }

    private void restartGame() throws Exception {
        System.out.println("Enter any key to restart");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        startGame();
    }
}
