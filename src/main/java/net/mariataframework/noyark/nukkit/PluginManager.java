package net.mariataframework.noyark.nukkit;


import cn.nukkit.plugin.PluginBase;



import java.io.File;
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

    private static final List<InstanceVO> vos = new ArrayList<>();


    public static void start(PluginBase base) throws Exception{

        File dataFolder = new File(base.getDataFolder()+"/plugin");
        if(!dataFolder.exists()){
            dataFolder.mkdirs();
        }
        File[] jarFile = dataFolder.listFiles();
        if(jarFile!=null){
            for(File file:jarFile){
                if(file.getName().endsWith(".jar")){
                   getManager().loadingPlugins(file,base);
                }
            }
        }
    }

    public static PluginManager getManager(){
        return manager;
    }

    public static void loadClasses() {
        for (InstanceVO vo : vos) {
            new ReflectSet(vo.getRootPackage(), vo.getDirFile()).loadAnnotation(vo.getClassLoader(), vo.getName());
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

    public List<InstanceVO> getVos(){
        return vos;
    }

    private void loadingPlugins(File file,PluginBase base) throws Exception{
        MariataClassLoader.getClassLoader().loadPlugin(file,base,this);
    }
}
