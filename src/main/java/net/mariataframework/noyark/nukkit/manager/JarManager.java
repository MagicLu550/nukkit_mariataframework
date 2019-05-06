package net.mariataframework.noyark.nukkit.manager;


import cn.nukkit.plugin.PluginBase;


import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;

public interface JarManager {
    void loadClass(Object obj,Class<?> clz);

    List<String> getJars();

    List<String> getPlugins();


    List<Class<?>> getMainClass();

    Map<Class<?>,Object> getInstances();

    void start(PluginBase base, boolean loadClass) throws Exception;

    void loadClasses(String[] rootPackage, String dirFile, URLClassLoader loader, String name);
}
