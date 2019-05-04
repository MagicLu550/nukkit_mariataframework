package net.mariataframework.noyark.nukkit;

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

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String[] getRootPackage() {
        return rootPackage;
    }

    public void setRootPackage(String[] rootPackage) {
        this.rootPackage = rootPackage;
    }

    public String getRootClass() {
        return rootClass;
    }

    public void setRootClass(String rootClass) {
        this.rootClass = rootClass;
    }
}
