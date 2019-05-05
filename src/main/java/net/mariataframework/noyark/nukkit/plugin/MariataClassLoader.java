package net.mariataframework.noyark.nukkit.plugin;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import net.mariataframework.noyark.nukkit.utils.Message;
import net.mariataframework.noyark.nukkit.manager.OamlManager;
import net.mariataframework.noyark.nukkit.manager.PluginManager;
import net.mariataframework.noyark.nukkit.utils.UnJar;
import net.mariataframework.noyark.nukkit.exception.NoConfigException;
import net.mariataframework.noyark.nukkit.vo.MariataOmlVO;

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

    public void loadPlugin(File file, PluginBase base, PluginManager manager,boolean loadClass) throws Exception{
        URL xURL = file.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{xURL});
        InputStream in = classLoader.getResourceAsStream("mariata.oml");
        if(in == null){
            throw new NoConfigException("No config named: mariata.oml");

        }
        String name = file.getName().substring(0,file.getName().indexOf(".jar"));

        String dirFile = base.getDataFolder()+"/plugin/"+name;
        new File(dirFile).mkdir();
        manager.getJars().add(dirFile);

        UnJar.decompress(file.getPath(),dirFile+"/");

        MariataOmlVO mariataOmlVO = OamlManager.getManager().toDoSet(in);

        if(loadClass) {
            load(mariataOmlVO, dirFile, manager, classLoader, name);
        }
        if(!mariataOmlVO.getRootClass().equals("")){

            manager.getMainClass().add(classLoader.loadClass(mariataOmlVO.getRootClass()));

        }else{

            throw new ClassNotFoundException("no class: main");

        }
        classLoader.close();
    }


    private void load(MariataOmlVO mariataOmlVO,String dirFile,PluginManager manager,URLClassLoader classLoader,String name) throws Exception{

        PluginManager.loadClasses(mariataOmlVO.getRootPackage(),dirFile+"/",classLoader,name);

        Message.loading(mariataOmlVO.getPluginName()+" ....",null);

        manager.getPlugins().add(TextFormat.GREEN+mariataOmlVO.getPluginName());

    }

}
