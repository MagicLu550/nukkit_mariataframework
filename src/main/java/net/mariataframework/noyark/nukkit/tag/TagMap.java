package net.mariataframework.noyark.nukkit.tag;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;

public interface TagMap {

    void putCompandTag(Item item, CompoundTag compoundTag);

    CompoundTag getCompandTag(Item item);
}
