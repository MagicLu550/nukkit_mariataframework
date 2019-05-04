package net.mariataframework.noyark.nukkit.utils;

public class Message {


    public static void println(String message){
        System.out.println("\n[mariata.framework ]"+message);
    }

    public static void start(){
        println("the mariata framework is loading...");
        println("--------NOYARK SYSTEM FRAMEWORK 0.1---------");
        println("author:magiclu550");
    }

    public static void loading(String message,Class<?> clz){
        println("loading "+message+": "+(clz==null?"":clz.getSimpleName()));
    }

    public static void end(){
        println("plugin is end");
        println("un the jar file");
    }
}
