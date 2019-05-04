package net.mariataframework.noyark.nukkit;


import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;
import cn.nukkit.utils.TextFormat;
import net.mariataframework.noyark.nukkit.exception.NoConfigException;
import net.noyark.oaml.OamlReader;
import net.noyark.oaml.tree.Document;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;


public class MariataClassLoader {


    private static OamlReader reader;

    private static final List<String> plugins = new ArrayList<>();
    private static final List<String> jars = new ArrayList<>();

    private static final List<Class<?>> mainClass = new ArrayList<>();

    private static final Logger logger = FrameworkCore.getInstance().getLogger();

    public static void start(PluginBase base) throws Exception{

        File dataFolder = new File(base.getDataFolder()+"/plugin");
        if(!dataFolder.exists()){
            dataFolder.mkdirs();
        }
        File[] jarFile = dataFolder.listFiles();
        if(jarFile!=null){
            for(File file:jarFile){
                if(file.getName().endsWith(".jar")){
                    URL xURL = file.toURI().toURL();
                    URLClassLoader classLoader = new URLClassLoader(new URL[]{xURL});
                    InputStream in = classLoader.getResourceAsStream("mariata.oml");
                    if(in == null){
                        throw new NoConfigException("No config named: mariata.oml");
                    }
                    String name = file.getName().substring(0,file.getName().indexOf(".jar"));
                    String dirFile = base.getDataFolder()+"/plugin/"+name;
                    jars.add(dirFile);
                    UnJar.decompress(file.getPath(),dirFile+"/");
                    reader = new OamlReader();
                    Document document = reader.read(in);
                    String pluginName = document.getEntry("name").getValue();
                    String rootPackage = document.getEntry("package").getValue();
                    new ReflectSet(rootPackage,dirFile+"/").loadAnnotation(classLoader,name);
                    String rootClass = document.getEntry("main").getValue();
                    if(!rootClass.equals("")){
                        mainClass.add(classLoader.loadClass(rootClass));
                    }else{
                        throw new ClassNotFoundException("no class: main");
                    }
                    Message.loading(pluginName+" ....",null);
                    plugins.add(TextFormat.GREEN+pluginName);
                    classLoader.close();
                }
            }
        }
    }


    public static List<String> getJars(){
        return jars;
    }

    public static List<String> getPlugins(){
        return plugins;
    }

    public static OamlReader getReader(){
        return reader;
    }
    public static List<Class<?>> getMainClass(){
        return mainClass;
    }
}
