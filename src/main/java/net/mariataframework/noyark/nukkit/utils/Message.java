package net.mariataframework.noyark.nukkit.utils;

import cn.nukkit.utils.LogLevel;
import net.mariataframework.noyark.nukkit.core.FrameworkCore;
import org.fusesource.jansi.Ansi;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.fusesource.jansi.Ansi.ansi;

public class Message {


    public static void println(String message){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        FrameworkCore.getInstance().getLogger().log(LogLevel.DEFAULT_LEVEL,ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(sdf.format(new Date())+" ").fg(Ansi.Color.WHITE).a("[").fg(Ansi.Color.GREEN).a("MARIATA>FRAMEWORK ").reset()+"]"+message);
    }

    public static void start(){

        println("the mariata framework is loading...\n\n");
        println("\n\n _____               .__        __          \n" +
                "  /     \\ _____ _______|__|____ _/  |______   \n" +
                " /  \\ /  \\\\__  \\\\_  __ \\  \\__  \\\\   __\\__  \\  \n" +
                "/    Y    \\/ __ \\|  | \\/  |/ __ \\|  |  / __ \\_\n" +
                "\\____|__  (____  /__|  |__(____  /__| (____  /\n" +
                "        \\/     \\/              \\/          \\/ \n\n");
        println("\n\n--------NOYARK SYSTEM FRAMEWORK 0.1---------");
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
