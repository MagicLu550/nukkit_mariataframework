package net.mariataframework.noyark.nukkit.core;

import cn.nukkit.plugin.PluginBase;
import net.mariataframework.noyark.nukkit.manager.OamlManager;
import net.mariataframework.noyark.nukkit.utils.ReflectCreate;
import net.mariataframework.noyark.nukkit.utils.ReflectSet;
import net.mariataframework.noyark.nukkit.vo.MariataOmlVO;

import java.io.IOException;
import java.io.InputStream;


public class MariataPluginBase extends PluginBase {




    public void getReflectLoader(PluginBase base, ReflectCreate create,String... rootPackage){

        try{
            //TODO
            ClassLoader classLoader = base.getClass().getClassLoader();
            InputStream in = classLoader.getResourceAsStream("mariata.oml");
            if(in == null){
                return;
            }
            MariataOmlVO vo = OamlManager.getManager().toDoSet(in);
            new ReflectSet(rootPackage,FrameworkCore.getInstance().getDataFolder()+"/plugin/"+vo.getPluginName()).loadAnnotation(classLoader,vo.getPluginName(),create);
        }catch (IOException io){
        }
    }

}
