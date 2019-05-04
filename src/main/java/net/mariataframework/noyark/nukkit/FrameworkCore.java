package net.mariataframework.noyark.nukkit;

import cn.nukkit.plugin.PluginBase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * marita-framework是一款基于Nukkit的解藕框架，该框架可以
 * 控制反转的创建javabean对象，不需要开发者手动创建一些对象
 */
public class FrameworkCore extends PluginBase {

    private static FrameworkCore frameworkCore;

    private static List<PluginBase> pluginInstance = new ArrayList<>();

    @Override
    public void onLoad() {
        try{
            frameworkCore = this;
            PluginManager.start(this);
            for(Class<?> clz: PluginManager.getMainClass()){
                Object o = clz.newInstance();
                if(o instanceof PluginBase) {
                    pluginInstance.add((PluginBase)o);
                }
            }
            for(PluginBase base:pluginInstance){
                base.getClass().getMethod("onLoad").invoke(base);
            }
        }catch (Exception e){
        }
    }

    @Override
    public void onEnable(){
        try{

            int start = 0;
            for(PluginBase base:pluginInstance){
                base.init(this.getPluginLoader(),this.getServer(),this.getDescription(),new File(this.getDataFolder()+"/"+ PluginManager.getPlugins().get(start)),this.getFile());
                start++;
            }

            PluginManager.loadClasses();


            for(PluginBase base:pluginInstance){
                base.getClass().getMethod("onEnable").invoke(base);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try{
            for(PluginBase base:pluginInstance){
                base.getClass().getMethod("onDisable").invoke(base);
            }
            Message.end();
            List<String> jars = PluginManager.getJars();
            for(String jar:jars){
                UnJar.clean(jar);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static FrameworkCore getInstance(){
        return frameworkCore;
    }
}
