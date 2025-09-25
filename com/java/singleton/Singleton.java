package com.java.singleton;

public class Singleton {
    private Singleton(){};
    private static Singleton instance;

    public synchronized static Singleton getInstance(){
        if (instance == null) {
            synchronized (Singleton.class) {
                if(instance == null){
                    instance = new Singleton();
                }
                return instance;
            }
        }
        return instance;
    }
}
