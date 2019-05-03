package net.mariataframework.noyark.nukkit;

/**
 * 如果想在服务器启动时做些操作，可以实现这个接口
 */
public interface MariataStart {

    void onLoad();

    void onEnable();

    void onDisable();
}
