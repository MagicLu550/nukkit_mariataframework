package net.mariataframework.noyark.nukkit.manager;

import net.mariataframework.noyark.nukkit.vo.MariataOmlVO;

import java.io.IOException;
import java.io.InputStream;

public interface VOManager {
    public MariataOmlVO toDoSet(InputStream in) throws IOException;
}
