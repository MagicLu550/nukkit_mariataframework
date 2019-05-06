package net.mariataframework.noyark.nukkit.plugin;

import cn.nukkit.plugin.PluginBase;
import net.mariataframework.noyark.nukkit.manager.PluginManager;

import java.io.File;

public interface MariataLoader {

    void loadPlugin(File file, PluginBase base, PluginManager manager, boolean loadClass) throws Exception;


}
