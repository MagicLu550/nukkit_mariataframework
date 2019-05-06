package net.mariataframework.noyark.nukkit.plugin;

import cn.nukkit.plugin.PluginBase;
import net.mariataframework.noyark.nukkit.manager.PluginManager;

import java.io.File;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;

public interface MariataLoader {

    void loadPlugin(File file, PluginBase base, PluginManager manager, boolean loadClass) throws Exception;

    List<URLClassLoader> getLoaders();

    Map<Class<?>,String> getPlugin_fileName();

}
