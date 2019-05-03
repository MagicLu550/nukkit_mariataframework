package net.mariataframework.noyark.nukkit;

public class Message {


    public static void println(String message){
        System.out.println("[mariata.framework]"+message);
    }

    public static void start(){
        println("\nthe mariata framework is loading...");
        println("--------NOYARK SYSTEM FRAMEWORK 0.1---------");
        println("author:magiclu550");
    }

    public static void loading(String message,Class<?> clz){
        println("loading "+message+": "+(clz.getSimpleName()==null?"":clz.getSimpleName()));
    }


    public static void end(){
        println("plugin is end");
    }
}
