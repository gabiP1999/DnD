package com.Games;

public interface LevelSubject {
    public void register(LevelObserver o);
    public void unregister(LevelObserver o);
}
