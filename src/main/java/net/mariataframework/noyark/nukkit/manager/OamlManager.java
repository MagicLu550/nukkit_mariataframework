package net.mariataframework.noyark.nukkit.manager;

import net.mariataframework.noyark.nukkit.vo.MariataOmlVO;
import net.noyark.oaml.OamlReader;
import net.noyark.oaml.tree.Document;
import net.noyark.oaml.tree.Node;

import java.io.IOException;
import java.io.InputStream;

public class OamlManager {



    private static OamlManager manager;

    static {
        manager = new OamlManager();
    }

    public MariataOmlVO toDoSet(InputStream in) throws IOException {
        OamlReader reader = new OamlReader();
        Document document = reader.read(in);
        String pluginName = document.getEntry("name").getValue();
        Node node =  document.getEntry("package");
        String[] rootPackage;
        if(node.isArray()){
            rootPackage = document.getEntry("package").getArray();
        }else{
            rootPackage = new String[]{document.getEntry("package").getValue()};
        }
        String rootClass = document.getEntry("main").getValue();
        return new MariataOmlVO(pluginName,rootPackage,rootClass);
    }

    public static OamlManager getManager(){
        return manager;
    }
}
