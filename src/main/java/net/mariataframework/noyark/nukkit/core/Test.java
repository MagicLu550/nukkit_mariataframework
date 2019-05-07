package net.mariataframework.noyark.nukkit.core;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.IntTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.NumberTag;
import net.mariataframework.noyark.nukkit.tag.MCompoundTagMap;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        MCompoundTagMap map = new MCompoundTagMap("name");
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("start",11);
        compoundTag.putInt("start1",11);
        compoundTag.putInt("start2",11);
        CompoundTag tag = new CompoundTag();
        tag.putInt("end",22);
        tag.putInt("end1",23);
        compoundTag.putCompound("tag",tag);
        ListTag<NumberTag> list = new ListTag<>("name");
        list.add(new IntTag("name",11));
        list.add(new IntTag("name",11));
        compoundTag.putList(list);
        Item item = new Item(11);
        map.putCompandTag(item,compoundTag);
        System.out.println(map.getCompandTag(item).get("start2"));
        System.out.println();
    }
}
