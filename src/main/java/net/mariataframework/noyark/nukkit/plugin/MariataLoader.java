package net.mariataframework.noyark.nukkit.plugin;

import cn.nukkit.plugin.PluginBase;
import net.mariataframework.noyark.nukkit.manager.PluginManager;

import java.io.File;
import java.net.URLClassLoader;
import java.util.List;

public interface MariataLoader {

    void loadPlugin(File file, PluginBase base, PluginManager manager, boolean loadClass) throws Exception;

    List<URLClassLoader> getLoaders();

}
