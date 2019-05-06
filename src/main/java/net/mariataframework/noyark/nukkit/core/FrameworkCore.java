package net.mariataframework.noyark.nukkit.core;


import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import net.mariataframework.noyark.nukkit.manager.BeanManager;
import net.mariataframework.noyark.nukkit.manager.PluginManager;
import net.mariataframework.noyark.nukkit.utils.Message;
import net.mariataframework.noyark.nukkit.utils.UnJar;

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
    @SuppressWarnings("unchecked")
    public void onLoad() {
        //加载前置插件
        try{
            File depend = new File(this.getDataFolder()+"/depend/");
            if(!depend.exists()){
                depend.mkdirs();
            }
            File[] files = depend.listFiles();
            if(files!=null){
                for(File file:files){
                    this.getPluginLoader().loadPlugin(file);
                }
            }
            String configFileName = this.getDataFolder()+"/mariataSet.yml";
            Config config = new Config(configFileName,Config.YAML);
            if(new File(configFileName).exists()){
                config.save();
            }
            List<String> list = config.getList("startbefore");
            if(list!=null){
                for(String fileName:list){
                    this.getPluginLoader().loadPlugin(fileName);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //TODO 暂时还没解决OnLoad方法问题，待解决方案
    //在第一次加载时保留ClassLoader，第二次调用后再释放
    @Override
    public void onEnable(){
        try{
            try{
                frameworkCore = this;
                Message.start();
                PluginManager.getManager().start(this,true);
                for(Class<?> clz: PluginManager.getManager().getMainClass()){
                    Object o = clz.newInstance();
                    if(o instanceof PluginBase) {
                        pluginInstance.add((PluginBase)o);
                    }
                }
                for(PluginBase base:pluginInstance){
                    base.getClass().getMethod("onLoad").invoke(base);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            int start = 0;
            for(PluginBase base:pluginInstance){
                base.init(this.getPluginLoader(),this.getServer(),this.getDescription(),new File(this.getDataFolder()+"/"+ PluginManager.getManager().getPlugins().get(start)),this.getFile());
                start++;
            }
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
            List<String> jars = PluginManager.getManager().getJars();
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

    public BeanManager getBeanManager(){
        return BeanManager.getManager();
    }
}
