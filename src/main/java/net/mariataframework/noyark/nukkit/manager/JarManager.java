package net.mariataframework.noyark.nukkit.manager;


import cn.nukkit.plugin.PluginBase;


import java.net.URLClassLoader;
import java.util.List;

public interface JarManager {
    void loadClass(Object obj,Class<?> clz);

    List<String> getJars();

    List<String> getPlugins();


    List<Class<?>> getMainClass();

    void start(PluginBase base, boolean loadClass) throws Exception;

    void loadClasses(String[] rootPackage, String dirFile, URLClassLoader loader, String name);
}
