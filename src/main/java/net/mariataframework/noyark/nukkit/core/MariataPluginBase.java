package net.mariataframework.noyark.nukkit.core;

import cn.nukkit.plugin.PluginBase;
import net.mariataframework.noyark.nukkit.manager.OamlManager;
import net.mariataframework.noyark.nukkit.utils.ReflectCreate;
import net.mariataframework.noyark.nukkit.utils.ReflectSet;
import net.mariataframework.noyark.nukkit.vo.MariataOmlVO;

import java.io.IOException;
import java.io.InputStream;


public class MariataPluginBase extends PluginBase {

    private String jarFileName;

    public String getJarFileName() {
        return jarFileName;
    }

    public void setJarFileName(String jarFileName) {
        this.jarFileName = jarFileName;
    }

    public void getReflectLoader(PluginBase base, ReflectCreate create, String... rootPackage){

        try{
            ClassLoader classLoader = base.getClass().getClassLoader();
            InputStream in = classLoader.getResourceAsStream("mariata.oml");
            if(in == null){
                return;
            }
            MariataOmlVO vo = OamlManager.getManager().toDoSet(in);
            new ReflectSet(rootPackage,FrameworkCore.getInstance().getDataFolder()+"/plugin/"+jarFileName+"/").loadAnnotation(classLoader,vo.getPluginName(),create);
        }catch (IOException io){
        }
    }

}
