package net.mariataframework.noyark.nukkit;

import java.net.URLClassLoader;

public class InstanceVO {
    private String rootPackage;

    private String dirFile;

    private URLClassLoader classLoader;

    private String name;


    public InstanceVO(String rootPackage, String dirFile, URLClassLoader classLoader, String name) {
        this.rootPackage = rootPackage;
        this.dirFile = dirFile;
        this.classLoader = classLoader;
        this.name = name;
    }


    public String getRootPackage() {
        return rootPackage;
    }



    public String getDirFile() {
        return dirFile;
    }


    public URLClassLoader getClassLoader() {
        return classLoader;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

