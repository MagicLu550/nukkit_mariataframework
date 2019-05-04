package net.mariataframework.noyark.nukkit;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import net.mariataframework.noyark.nukkit.exception.NoConfigException;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class MariataClassLoader {

    private static MariataClassLoader mcl;

    static{
        mcl = new MariataClassLoader();
    }

    private MariataClassLoader(){

    }

    public static MariataClassLoader getClassLoader(){
        return mcl;
    }

    public void loadPlugin(File file, PluginBase base,PluginManager manager) throws Exception{
        URL xURL = file.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{xURL});

        InputStream in = classLoader.getResourceAsStream("mariata.oml");

        if(in == null){

            throw new NoConfigException("No config named: mariata.oml");

        }
        String name = file.getName().substring(0,file.getName().indexOf(".jar"));

        String dirFile = base.getDataFolder()+"/plugin/"+name;

        manager.getJars().add(dirFile);

        UnJar.decompress(file.getPath(),dirFile+"/");

        load(in,dirFile,manager,classLoader,name);

        classLoader.close();
    }


    private void load(InputStream in,String dirFile,PluginManager manager,URLClassLoader classLoader,String name) throws Exception{

        MariataOmlVO mariataOmlVO = OamlManager.getManager().toDoSet(in);

        for(String rootPackage:mariataOmlVO.getRootPackage()){

            manager.getVos().add(new InstanceVO(rootPackage,dirFile+"/",classLoader,name));

        }

        if(!mariataOmlVO.getRootClass().equals("")){

            manager.getMainClass().add(classLoader.loadClass(mariataOmlVO.getRootClass()));

        }else{

            throw new ClassNotFoundException("no class: main");

        }

        Message.loading(mariataOmlVO.getPluginName()+" ....",null);

        manager.getPlugins().add(TextFormat.GREEN+mariataOmlVO.getPluginName());

    }

}
