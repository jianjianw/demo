package com.example.demo;
/**
 * 保存一个线程安全的DatabaseType容器
 * @Author xuelongjiang
 */
public class DatabaseContextHolder {

    private static final  ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType type){
            contextHolder.set(type);
    }

    public static  DatabaseType getDatabaseType(){
        return contextHolder.get();
    }

}