package net.mariataframework.noyark.nukkit;

import cn.nukkit.plugin.PluginBase;


/**
 *
 * marita-framework是一款基于Nukkit的解藕框架，该框架可以
 * 控制反转的创建javabean对象，不需要开发者手动创建一些对象
 * ListenerHandler注解：标记这是个监听器
 * CommandHandler注解: 标记这是个指令
 */
public class FrameworkCore extends PluginBase {

    private static FrameworkCore frameworkCore;

    static{
        frameworkCore = new FrameworkCore();
    }

    @Override
    public void onLoad() {
        try{
            Message.start();
            MariataClassLoader.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onDisable() {
        Message.end();
    }

    public static FrameworkCore getInstance(){
        return frameworkCore;
    }
}
