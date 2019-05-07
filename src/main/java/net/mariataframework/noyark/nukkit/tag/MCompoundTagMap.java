package net.mariataframework.noyark.nukkit.tag;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.*;
import net.mariataframework.noyark.nukkit.core.FrameworkCore;
import net.noyark.oaml.DocumentFactory;
import net.noyark.oaml.OamlReader;
import net.noyark.oaml.OamlWriter;
import net.noyark.oaml.tree.Document;
import net.noyark.oaml.tree.Node;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author MagicLu550
 *
 * see magic.noyark.net
 * */

public class MCompoundTagMap implements TagMap{


    private static final byte TAG_End = 0;
    private static final byte TAG_Byte = 1;
    private static final byte TAG_Short = 2;
    private static final byte TAG_Int = 3;
    private static final byte TAG_Long = 4;
    private static final byte TAG_Float = 5;
    private static final byte TAG_Double = 6;
    private static final byte TAG_Byte_Array = 7;
    private static final byte TAG_String = 8;
    private static final byte TAG_List = 9;
    private static final byte TAG_Compound = 10;
    private static final byte TAG_Int_Array = 11;

    public String FILE;

    private Map<Item, CompoundTag> tags = new HashMap<>();



    public MCompoundTagMap(String name){
        FILE = FrameworkCore.getInstance().getDataFolder()+"/tag+"+name+"+/ItemTag.tag";
    }

    private Map<Tag,StringBuilder> tag_path = new HashMap<>();

    public void putCompandTag(Item item,CompoundTag compoundTag){
        try{
            Document document = putCompandTag(item.hashCode()+"",compoundTag,DocumentFactory.getDocument());
            OamlWriter writer = new OamlWriter(new FileOutputStream(FILE));
            writer.write(document);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Document putCompandTag(String hashcode,CompoundTag ctag,Document document) throws IOException {
        Collection<Tag> tags = ctag.getAllTags();

        Node node = document.getEntry(hashcode);

        Node nodeTYPE = document.getEntry("TYPE"+hashcode);

        for(Tag tag:tags){

            putTag(tag,node,nodeTYPE);
        }
        return document;
    }


    private void putTag(Tag tag,Node node,Node nodeTYPE) throws IOException{

        //存储标签
        Node tagNode = node.createNode(tag.getName()==""?tag.hashCode()+"":tag.getName());
        //存储类型
        Node typeNode = nodeTYPE.createNode(tag.getName()==""?tag.hashCode()+"":tag.getName());

        typeNode.setValue(tag.getId()+"");
        if(tag instanceof CompoundTag){
            Collection<Tag> tags = ((CompoundTag) tag).getAllTags();
            for(Tag tag1:tags){
                putTag(tag1,tagNode,typeNode);
            }
        }else if(tag instanceof NumberTag){
            tagNode.setValue(((NumberTag) tag).getData()+"");
        }else if(tag instanceof ByteArrayTag){
            tagNode.setArray(((ByteArrayTag) tag).data);
        }else if(tag instanceof IntArrayTag){
            tagNode.setArray(((IntArrayTag) tag).data);
        }else if(tag instanceof ListTag){
            for(int i = 0;i<((ListTag) tag).size();i++){
                Tag fromTag = ((ListTag) tag).get(i);
                putTag(fromTag,tagNode,typeNode);
            }
        }
    }

    public CompoundTag getCompandTag(Item item){
        CompoundTag tag = new CompoundTag();
        try{

            OamlReader reader = new OamlReader();
            Document document = reader.read(FILE);
            List<Node> tagNodes = document.getEntry(item.hashCode()+"").getSons();

            List<Node> typeNodes = document.getEntry("TYPE"+item.hashCode()+"").getSons();

            int start = 0;
            for(Node tagNode:tagNodes){
                Node typeNode = typeNodes.get(start);
                putTag(typeNode,tagNode,tag);
                start++;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return tag;
    }

    private void putTag(Node typeNode,Node tagNode,CompoundTag tag){
        switch (Integer.parseInt(typeNode.getValue())){
            case TAG_End:
                tag.put(tagNode.getName(),new EndTag());
                break;
            case TAG_Byte:
                tag.put(tagNode.getName(),new ByteTag(tagNode.getName(),Byte.parseByte(tagNode.getValue())));
                break;
            case TAG_Short:
                tag.put(tagNode.getName(),new ShortTag(tagNode.getName(),Short.parseShort(tagNode.getValue())));
                break;
            case TAG_Int:
                tag.put(tagNode.getName(),new IntTag(tagNode.getName(),Integer.parseInt(tagNode.getValue())));
                break;
            case TAG_Byte_Array:
                String[] byteArray = tagNode.getArray();
                byte[] bytes = new byte[byteArray.length];
                int i = 0;
                for(String str:byteArray){
                    bytes[i] = Byte.parseByte(str);
                    i++;
                }
                tag.put(tagNode.getName(),new ByteArrayTag(tagNode.getName(),bytes));
                break;
            case TAG_Int_Array:
                String[] intArray = tagNode.getArray();
                int[] ints = new int[intArray.length];
                int j = 0;
                for(String str:intArray){
                    ints[j] = Integer.parseInt(str);
                }
                tag.put(tagNode.getName(),new IntArrayTag(tagNode.getName(),ints));
                break;
            case TAG_Float:
                tag.put(tagNode.getName(),new FloatTag(tagNode.getName(),Float.parseFloat(tagNode.getValue())));
                break;
            case TAG_List:
                List<Node> tagChilds = tagNode.getSons();
                List<Node> typeNodes = typeNode.getSons();
                ListTag listTag = new ListTag(tagNode.getName());
                int start = 0;
                for(Node node:tagChilds){
                    Node type = typeNodes.get(start);
                    putTag(type,node,listTag);
                    start++;
                }
                tag.put(tagNode.getName(),listTag);
                break;
            case TAG_String:
                tag.put(tagNode.getName(),new StringTag(tagNode.getName(),tagNode.getValue()));
                break;
            case TAG_Long:
                tag.put(tagNode.getName(),new LongTag(tagNode.getName(),Long.parseLong(tagNode.getValue())));
                break;
            case TAG_Double:
                tag.put(tagNode.getName(),new DoubleTag(tagNode.getName(),Double.parseDouble(tagNode.getValue())));
                break;
            case TAG_Compound:
                List<Node> tagCompoundChilds = tagNode.getSons();
                List<Node> typeCompoundNodes = typeNode.getSons();
                CompoundTag ctag = new CompoundTag(tagNode.getName());
                int z = 0;
                for(Node node:tagCompoundChilds){
                    Node type = typeCompoundNodes.get(z);
                    putTag(type,node,ctag);
                    z++;
                }
                tag.put(tagNode.getName(),ctag);
                break;
        }
    }
    private void putTag(Node typeNode,Node tagNode,ListTag tag){
        switch (Integer.parseInt(typeNode.getValue())){
            case TAG_End:
                tag.add(new EndTag());
                break;
            case TAG_Byte:
                tag.add(new ByteTag(tagNode.getName(),Byte.parseByte(tagNode.getValue())));
                break;
            case TAG_Short:
                tag.add(new ShortTag(tagNode.getName(),Short.parseShort(tagNode.getValue())));
                break;
            case TAG_Int:
                tag.add(new IntTag(tagNode.getName(),Integer.parseInt(tagNode.getValue())));
                break;
            case TAG_Byte_Array:
                String[] byteArray = tagNode.getArray();
                byte[] bytes = new byte[byteArray.length];
                int i = 0;
                for(String str:byteArray){
                    bytes[i] = Byte.parseByte(str);
                    i++;
                }
                tag.add(new ByteArrayTag(tagNode.getName(),bytes));
                break;
            case TAG_Int_Array:
                String[] intArray = tagNode.getArray();
                int[] ints = new int[intArray.length];
                int j = 0;
                for(String str:intArray){
                    ints[j] = Integer.parseInt(str);
                }
                tag.add(new IntArrayTag(tagNode.getName(),ints));
                break;
            case TAG_Float:
                tag.add(new FloatTag(tagNode.getName(),Float.parseFloat(tagNode.getValue())));
                break;
            case TAG_List:
                List<Node> tagChilds = tagNode.getSons();
                List<Node> typeNodes = typeNode.getSons();
                ListTag listTag = new ListTag();
                int start = 0;
                for(Node node:tagChilds){
                    Node type = typeNodes.get(start);
                    putTag(type,node,tag);
                    start++;
                }
                tag.add(listTag);
                break;
            case TAG_String:
                break;
            case TAG_Long:
                break;
            case TAG_Double:
                break;
            case TAG_Compound:
                break;
        }
    }
}
