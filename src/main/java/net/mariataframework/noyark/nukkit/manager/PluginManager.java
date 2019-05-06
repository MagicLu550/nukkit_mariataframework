package net.mariataframework.noyark.nukkit.manager;


import cn.nukkit.command.Command;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import net.mariataframework.noyark.nukkit.core.FrameworkCore;
import net.mariataframework.noyark.nukkit.utils.Message;
import net.mariataframework.noyark.nukkit.utils.ReflectSet;
import net.mariataframework.noyark.nukkit.plugin.MariataClassLoader;


import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;


public class PluginManager {

    private static PluginManager manager;

    static {
        manager = new PluginManager();
    }


    private static final List<String> plugins = new ArrayList<>();

    private static final List<String> jars = new ArrayList<>();

    private static final List<Class<?>> mainClass = new ArrayList<>();



    public static void start(PluginBase base,boolean loadClass) throws Exception{

        File dataFolder = new File(base.getDataFolder()+"/plugin");
        if(!dataFolder.exists()){
            dataFolder.mkdirs();
        }
        File[] jarFile = dataFolder.listFiles();
        if(jarFile!=null){
            for(File file:jarFile){
                if(file.getName().endsWith(".jar")){
                   getManager().loadingPlugins(file,base,loadClass);
                }
            }
        }
    }

    public static PluginManager getManager(){
        return manager;
    }

    public static void loadClasses(String[] rootPackage, String dirFile, URLClassLoader loader, String name) {

        new ReflectSet(rootPackage, dirFile).loadAnnotation(loader, name,(obj,clz)-> {
            PluginManager.getManager().loadClass(obj,clz);
        });
    }

    public void loadClass(Object obj,Class<?> clz){
        Message.loading(obj.getClass().getName(),obj.getClass());
        if(obj instanceof Listener){
            FrameworkCore.getInstance().getServer().getPluginManager().registerEvents((Listener) obj,FrameworkCore.getInstance());
        }
        if(obj instanceof Command){
            try{
                FrameworkCore.getInstance().getServer().getCommandMap().register(clz.getDeclaredMethod("setPrefix").toString(),(Command)obj);
            }catch (NoSuchMethodException e){
                FrameworkCore.getInstance().getServer().getCommandMap().register("",(Command)obj);
            }
        }
    }
    public List<String> getJars(){
        return jars;
    }

    public List<String> getPlugins(){
        return plugins;
    }


    public List<Class<?>> getMainClass(){
        return mainClass;
    }


    private void loadingPlugins(File file,PluginBase base,boolean loadClass) throws Exception{
        MariataClassLoader.getClassLoader().loadPlugin(file,base,this,loadClass);
    }
}
