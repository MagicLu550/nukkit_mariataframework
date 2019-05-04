package net.mariataframework.noyark.nukkit.manager;


import cn.nukkit.plugin.PluginBase;
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

        new ReflectSet(rootPackage, dirFile).loadAnnotation(loader, name);
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
