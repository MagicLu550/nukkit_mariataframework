package net.mariataframework.noyark.nukkit.plugin;


import cn.nukkit.event.Listener;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import net.mariataframework.noyark.nukkit.core.FrameworkCore;
import net.mariataframework.noyark.nukkit.core.MariataPluginBase;
import net.mariataframework.noyark.nukkit.tag.MCompoundTagMap;
import net.mariataframework.noyark.nukkit.utils.Message;
import net.mariataframework.noyark.nukkit.manager.OamlManager;
import net.mariataframework.noyark.nukkit.manager.PluginManager;
import net.mariataframework.noyark.nukkit.utils.ReflectSet;
import net.mariataframework.noyark.nukkit.utils.UnJar;
import net.mariataframework.noyark.nukkit.exception.NoConfigException;
import net.mariataframework.noyark.nukkit.vo.MariataOmlVO;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MariataClassLoader implements MariataLoader{


    private static MariataClassLoader mcl;

    private List<URLClassLoader> loaders = new ArrayList<>();

    private Map<Class<?>,String> plugin_fileName = new HashMap<>();

    static{
        mcl = new MariataClassLoader();
    }

    private MariataClassLoader(){
    }


    public static MariataLoader getClassLoader(){
        return mcl;
    }

    public void loadPlugin(File file, PluginBase base, PluginManager manager,boolean loadClass) throws Exception{
        URL xURL = file.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{xURL},this.getClass().getClassLoader());
        InputStream in = classLoader.getResourceAsStream("mariata.oml");
        boolean isDefault = false;
        if(in == null){
            in = classLoader.getResourceAsStream("plugin.yml");
            if(in == null){
                throw new NoConfigException("No config named: mariata.oml");
            }
            isDefault = true;
        }

        String name = file.getName().substring(0,file.getName().indexOf(".jar"));

        String dirFile = base.getDataFolder()+"/plugin/"+name;


        UnJar.decompress(file.getPath(),dirFile+"/");
        if(!isDefault){

            MariataOmlVO mariataOmlVO = OamlManager.getManager().toDoSet(in);

            if(!mariataOmlVO.getRootClass().equals("")){
                Class<?> pluginClass = classLoader.loadClass(mariataOmlVO.getRootClass());

                manager.getMainClass().add(pluginClass);

                readPluginBase(pluginClass,name,mariataOmlVO);

                plugin_fileName.put(pluginClass,name);
            }else{

                throw new ClassNotFoundException("no class: main");

            }
            if(loadClass) {

                load(mariataOmlVO, dirFile, manager, classLoader, name);

            }

        }else{

            /*
             *如果是普通插件则读取普通插件
             */

            Plugin pluginBase = base.getPluginLoader().loadPlugin(file);
            String packageName = pluginBase.getClass().getPackage().getName();

            new ReflectSet(new String[]{packageName.substring(0,packageName.indexOf(".")==-1?packageName.length():packageName.indexOf("."))},dirFile+"/").loadAnnotation(pluginBase.getClass().getClassLoader(),pluginBase.getName(),(obj,clz)->{
                PluginManager.getManager().loadClass(obj,clz);
            });
            PluginManager.getManager().getJars().add(dirFile+"/");
        }
        loaders.add(classLoader);
    }

    public List<URLClassLoader> getLoaders(){
        return loaders;
    }

    public Map<Class<?>,String> getPlugin_fileName(){
        return plugin_fileName;
    }

    private void load(MariataOmlVO mariataOmlVO,String dirFile,PluginManager manager,URLClassLoader classLoader,String name) throws Exception{

        PluginManager.getManager().loadClasses(mariataOmlVO.getRootPackage(),dirFile+"/",classLoader,name);

        Message.loading(mariataOmlVO.getPluginName()+" ....",null);

        manager.getPlugins().add(TextFormat.GREEN+mariataOmlVO.getPluginName());

    }

    private void readPluginBase(Class<?> pluginClass,String name,MariataOmlVO mariataOmlVO) throws Exception{
        Object o = pluginClass.newInstance();
        if(o instanceof PluginBase) {
            if (o instanceof MariataPluginBase) {
                ((MariataPluginBase) o).setJarFileName(name);
                ((MariataPluginBase) o).setTagMap(new MCompoundTagMap(mariataOmlVO.getPluginName()));
            }
            if(o instanceof Listener){
                FrameworkCore.getInstance().getServer().getPluginManager().registerEvents((Listener)o,FrameworkCore.getInstance());
            }
            FrameworkCore.getPluginInstance().add((PluginBase)o);
        }
    }
}
