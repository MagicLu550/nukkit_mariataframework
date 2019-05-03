package net.mariataframework.noyark.nukkit;


import cn.nukkit.utils.TextFormat;
import net.mariataframework.noyark.nukkit.exception.NoConfigException;
import net.noyark.oaml.OamlReader;
import net.noyark.oaml.tree.Document;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class MariataClassLoader {

    public static List<String> plugins;

    private static FrameworkCore core;
    static{
        core = FrameworkCore.getInstance();
    }
    public static void start() throws Exception{
        File[] jarFile = core.getDataFolder().listFiles();
        if(jarFile!=null){
            for(File file:jarFile){
                URL xURL = file.toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{xURL});
                InputStream in = classLoader.getResourceAsStream("mariata.oml");
                if(in == null){
                    throw new NoConfigException("No config named: mariata.oml");
                }
                OamlReader reader = new OamlReader();
                Document document = reader.read(in);
                String pluginName = document.getEntry("name").getValue();
                String rootPackage = document.getEntry("package").getValue();
                new ReflectSet(rootPackage,classLoader.getResource("/").getPath());
                Message.loading("loading "+pluginName+" ....",null);
                plugins.add(TextFormat.GREEN+pluginName);
            }
        }
    }
    public static List<String> getPlugins(){
        return plugins;
    }
}
