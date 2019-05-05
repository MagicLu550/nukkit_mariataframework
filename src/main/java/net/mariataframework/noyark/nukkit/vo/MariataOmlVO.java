package net.mariataframework.noyark.nukkit.vo;

import java.util.Arrays;

public class MariataOmlVO {

    private String pluginName;

    private String[] rootPackage;

    private String rootClass;

    public MariataOmlVO(String pluginName, String[] rootPackage, String rootClass) {
        this.pluginName = pluginName;
        this.rootPackage = rootPackage;
        this.rootClass = rootClass;
    }

    public String getPluginName() {
        return pluginName;
    }


    public String[] getRootPackage() {
        return rootPackage;
    }


    public String getRootClass() {
        return rootClass;
    }


    @Override
    public String toString() {
        return "MariataOmlVO{" +
                "pluginName='" + pluginName + '\'' +
                ", rootPackage=" + Arrays.toString(rootPackage) +
                ", rootClass='" + rootClass + '\'' +
                '}';
    }
}
