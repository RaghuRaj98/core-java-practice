package com.java.singleton;

public class SingletonDayTwo {
    private static SingletonDayTwo instance;
    private SingletonDayTwo() {}
    public static SingletonDayTwo getInstance(){
        if(instance==null){
            synchronized (SingletonDayTwo.class) {
                instance = new SingletonDayTwo();
                return instance;
            }
        }
        return instance;
    }
}
