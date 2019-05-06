package net.mariataframework.noyark.nukkit.manager;

public class BeanManager  {
    private static BeanManager manager;

    static {
        manager = new BeanManager();
    }


    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clzz){
        return (T)PluginManager.getManager().getInstances().get(clzz);
    }

    public static BeanManager getManager(){
        return manager;
    }
}
